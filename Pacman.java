import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Pacman extends Character {
    private char direction = 'R'; 
    final private int xpos = 9, ypos = 15;
    private boolean isWon = false;
    private boolean isGameOver = false;

    // --- Animation Variables 
    private double mouthOpenAngle = 0; 
    private double maxMouthAngle = 45;   
    private double chompSpeed = 4;        
    private boolean isMouthOpening = true;
    private boolean isDying = false;
    private double deathAngle = 360;


    public Pacman(double x, double y, double s) {
        super(x, y, s - 6); 
        image = Assets.pacRight;
    }

    public void setIsDying(boolean dying) {
        isDying = dying;
    }

    public boolean getIsDying() {
        return isDying;
    }

    public void setIsGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public boolean getIsWon() {
        return isWon;
    }

    public void setIsWon(boolean won) {
        isWon = won;
    }

    public void setdir(char dir) {
        direction = dir;
    }

    public char getdir() {
        return direction;
    }

    public void setDirection(double newVx, double newVy) {
        if (isDying || isGameOver || isWon) {
            setVx(0);
            setVy(0);
            return;
        }

        char nextDir = this.getdir();
        if (newVy < 0) nextDir = 'U';
        else if (newVy > 0) nextDir = 'D';
        else if (newVx < 0) nextDir = 'L';
        else if (newVx > 0) nextDir = 'R';
        if (nextDir == this.direction) {
            setVx(newVx);
            setVy(newVy);
            return;
        }
        double centerX = Math.round(getX() / GameMap.tile) * GameMap.tile;
        double centerY = Math.round(getY() / GameMap.tile) * GameMap.tile;

        if (Math.abs(getX() - centerX) < 8 && Math.abs(getY() - centerY) < 8) {
            setX(centerX);
            setY(centerY);
            setVx(newVx);
            setVy(newVy);
            setdir(nextDir);
        }
    }

    public void resetPacmanPosition() {
        setX(xpos * GameMap.tile);
        setY(ypos * GameMap.tile);
        setVx(0);
        setVy(0);
        direction = 'R';
        mouthOpenAngle = 0;
    }

    public void update(GameMap map) {
        if (isGameOver || isWon) {
            return;
        }

        // Handle Death Animation sequence
        if (isDying) {
            setVx(0);
            setVy(0);
            deathAngle -= 6; 
            
            if (deathAngle <= 0) {
                isDying = false;
                deathAngle = 360; 
                resetPacmanPosition();
            }
            return; 
        }

        // Save position before moving to verify actual wall blockages
        double prevX = getX();
        double prevY = getY();

        move(map);

        // Update mouth animation 
        if (getX() != prevX || getY() != prevY) {
            if (isMouthOpening) {
                mouthOpenAngle += chompSpeed;
                if (mouthOpenAngle >= maxMouthAngle) {
                    isMouthOpening = false;
                }
            } else {
                mouthOpenAngle -= chompSpeed;
                if (mouthOpenAngle <= 0) {
                    isMouthOpening = true;
                }
            }
        } else {
            mouthOpenAngle = maxMouthAngle / 2; // Stop chomping when hitting a wall or standing still
        }

        boolean foodsLeft = false;
        java.util.ArrayList<GameObject> objectsToRemove = new java.util.ArrayList<>();

        for (int i = 0; i < map.allObjects.size(); i++) {
            GameObject obj = map.allObjects.get(i);
            if (obj instanceof Food) {
                if (this.collide(obj)) {
                    objectsToRemove.add(obj);
                    map.score.add(10); 
                } else {
                    foodsLeft = true; 
                }
            }
            else if (obj instanceof Cherry) {
                if (this.collide(obj)) {
                    objectsToRemove.add(obj);
                    map.score.add(50);
                    
                    for (GameObject o : map.allObjects) {
                        if (o instanceof Ghost) {
                            Ghost g = (Ghost) o;
                            g.setScared(true);
                            g.setScaredUntil();
                        }
                    }
                }
            }
            else if (obj instanceof Ghost) {
                Ghost ghost = (Ghost) obj;
                if (ghost.isScared() && System.currentTimeMillis() > ghost.getScaredUntil()) {
                    ghost.setScared(false); 
                }
            }
        }

        if (!objectsToRemove.isEmpty()) {
            map.allObjects.removeAll(objectsToRemove);
        }

        if (!foodsLeft && !isWon) {
            isWon = true;
            map.score.saveHighScoreToDatabase();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (getIsDying()) {
            // death animation
            gc.save(); 
            gc.beginPath();
            gc.moveTo(getX() + getW()/2, getY() + getH()/2);
            gc.arc(getX() + getW()/2, getY() + getH()/2, getW()/2, getW()/2, 90, deathAngle);
            gc.closePath();
            gc.clip();
            gc.setFill(Color.YELLOW);
            gc.fillOval(getX(), getY(), getW(), getH());
            gc.restore(); 
        } else {
            gc.save();
            gc.setFill(Color.YELLOW);
            double startAngle = 0;
            double arcExtent = 360 - (mouthOpenAngle * 2);

            switch (direction) {
                case 'R': startAngle = mouthOpenAngle; break;
                case 'L': startAngle = 180 + mouthOpenAngle; break;
                case 'U': startAngle = 90 + mouthOpenAngle; break;
                case 'D': startAngle = 270 + mouthOpenAngle; break;
            }
            
            gc.fillArc(getX(), getY(), getW(), getH(), startAngle, arcExtent, ArcType.ROUND);
            gc.restore();
        }
        
        drawOverlay(gc, 608, 672);
    }

    public void drawOverlay(GraphicsContext gc, double width, double height) {
        if (getIsWon() || getIsGameOver()) {
            gc.save();
            gc.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.8));
            gc.fillRect(0, 0, width, height);

            gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 48));
            gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);

            if (getIsWon()) {
                gc.setFill(javafx.scene.paint.Color.YELLOW);
                gc.fillText("YOU WIN!", width / 2, height / 2 - 40);
            } else if (getIsGameOver()) {
                gc.setFill(javafx.scene.paint.Color.RED);
                gc.fillText("GAME OVER", width / 2, height / 2 - 40);
            }
            gc.setFont(javafx.scene.text.Font.font("Arial", 22));
            gc.setFill(javafx.scene.paint.Color.WHITE);
            gc.fillText("Press 'R' to Restart", width / 2, height / 2 + 100);
            
            gc.restore();
        }
    }
}