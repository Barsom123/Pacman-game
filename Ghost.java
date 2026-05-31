import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.ArrayList;

public abstract class Ghost extends Character {
    private long scaredUntil = 0;
    protected Image scaredImage = Assets.scaredGhost;
    private boolean scared = false;
    Random rand = new Random();
    
    private GameMap currentMap; 

    public Ghost(double x, double y, double s, Image img) {
        super(x, y, s);
        this.image = img;
    }

    public void setScared(boolean scared) {
        this.scared = scared;
    }

    public boolean isScared() {
        return scared;
    }

    public void setScaredUntil() {
        scaredUntil = System.currentTimeMillis() + 10000; 
    }

    public long getScaredUntil() {
        return scaredUntil;
    }

    abstract void chooseDirection(GameMap map);

    public void update(GameMap map) {
        this.currentMap = map; 
        if (isAtIntersection()) {
            chooseDirection(map);
        }
        
        move(map);

        if (collide(map.pacman)) {
            if (scared) {
                map.score.add(200);
            } else {
                map.lives.subtract(1);
                if (map.lives.getLives() <= 0) {
                    if (!map.pacman.getIsGameOver()) {
                        map.pacman.setIsGameOver(true);
                        map.score.saveHighScoreToDatabase();
                    }
                }
                map.pacman.setIsDying(true);
            }
            resetPosition();
        }
        
        this.currentMap = null; 
    }

    void resetPosition() {
        setX(9 * GameMap.tile);
        setY(9 * GameMap.tile);
    }

    @Override
    void draw(GraphicsContext gc) {
        if (scared) {
            gc.drawImage(scaredImage, getX(), getY(), getW(), getH());
        } else {
            gc.drawImage(image, getX(), getY(), getW(), getH());
        }
    }

    private boolean isPathClear(double vx, double vy, GameMap map) {
        double nextX = getX() + vx;
        double nextY = getY() + vy;

        for (GameObject obj : map.allObjects) {
            if (obj instanceof Wall) {
                if (nextX < obj.getX() + obj.getW() &&
                    nextX + getW() > obj.getX() &&
                    nextY < obj.getY() + obj.getH() &&
                    nextY + getH() > obj.getY()) {
                    return false;
                }
            }
        }
        return true;
    }
    void randomMove() {
        if (currentMap == null) return;

        ArrayList<int[]> validDirections = new ArrayList<>();
        ArrayList<int[]> preferredDirections = new ArrayList<>();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] d : dirs) {
            if (isPathClear(d[0], d[1], currentMap)) {
                validDirections.add(d);
                if (d[0] != -getVx() || d[1] != -getVy()) {
                    preferredDirections.add(d);
                }
            }
        }

        if (!preferredDirections.isEmpty()) {
            int[] chosen = preferredDirections.get(rand.nextInt(preferredDirections.size()));
            setVx(chosen[0]);
            setVy(chosen[1]);
        } else if (!validDirections.isEmpty()) {
            int[] chosen = validDirections.get(rand.nextInt(validDirections.size()));
            setVx(chosen[0]);
            setVy(chosen[1]);
        } else {
            setVx(0);
            setVy(0);
        }
    }

    @Override
    void onWallHit() {
        randomMove(); 
    }

    boolean isAtIntersection() {
        return (getX() % GameMap.tile == 0) && (getY() % GameMap.tile == 0);
    }
}