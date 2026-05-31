import javafx.scene.canvas.GraphicsContext;

public class Cherry extends GameObject {

    public Cherry(double x, double y) {
        super(x, y, GameMap.tile, GameMap.tile);
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(Assets.cherry, getX(), getY(), getW(), getH());
    }
}