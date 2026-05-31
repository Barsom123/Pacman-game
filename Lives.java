import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
class Lives{
    private int lives = 3;

    public void subtract(int x) {
        if (lives > 0)
            lives -= x;
    }
    public int getLives() {
        return lives;
    }
    public void resetLives() {
        lives = 3;
    }

}