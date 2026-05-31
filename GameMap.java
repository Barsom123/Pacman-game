import java.util.*;

public class GameMap {
    public ArrayList<GameObject> allObjects = new ArrayList<>();
    Pacman pacman;
    public static final int tile = 32;
    public static final int mapWidth = 19 * tile;
    public static final int mapHeight = 21 * tile;
    Score score;
    Lives lives;
    public GameMap(Score score, Lives lives) {
        this.score = score;
        this.lives = lives;
    }

    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "X      XbpoX      X",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    public void load() {
        allObjects.clear();
        allObjects.add(new Cherry(9 * tile, 11 * tile));
        allObjects.add(new Cherry(9 * tile, 5 * tile));

        for (int r = 0; r < tileMap.length; r++) {
            for (int c = 0; c < tileMap[r].length(); c++) {
                char ch = tileMap[r].charAt(c);
                int x = c * tile;
                int y = r * tile;

                switch (ch) {
                    case 'X': {
                        allObjects.add(new Wall(x, y, tile));
                        break;
                    }
                    case 'P':
                        pacman = new Pacman(x, y, tile);
                        allObjects.add(pacman); 
                        break;
                    case ' ':
                        allObjects.add(new Food(x + 14, y + 14));
                        break;
                    case 'b':
                        allObjects.add(new BlueGhost(x, y, tile));
                        break;
                    case 'r':
                        allObjects.add(new RedGhost(x, y, tile));
                        break;
                    case 'p':
                        allObjects.add(new PinkGhost(x, y, tile));
                        break;
                    case 'o':
                        allObjects.add(new OrangeGhost(x, y, tile));
                        break;
                    case 'O':
                        break;
                }
            }
        }
    }

    public void update() {
        if (pacman.getIsGameOver() || pacman.getIsWon()) {
            return;
        }

        pacman.update(this);

        for (int i = 0; i < allObjects.size(); i++) {
            GameObject obj = allObjects.get(i);
            if (obj instanceof Ghost) {
                ((Ghost) obj).update(this);
            }
        }
    }

    public void draw(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, tileMap[0].length() * tile, tileMap.length * tile);

        for (GameObject obj : allObjects) {
            obj.draw(gc);
        }
    }

    public void reset() {
        pacman.setIsGameOver(false);
        pacman.setIsWon(false);
        pacman.setIsDying(false); 
        pacman.resetPacmanPosition();
        load(); 
        lives.resetLives(); 
        score.resetScore(); 
    }
}