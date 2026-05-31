import javafx.scene.canvas.GraphicsContext;

public class Food extends GameObject {

    public Food(double x, double y) {
        super(x,y,4,4);
    }

    void draw(GraphicsContext gc) {
        gc.drawImage(Assets.food, getX(), getY(), getW(), getH());
    }
}