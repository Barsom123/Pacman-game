import javafx.scene.canvas.GraphicsContext;

public class Wall extends GameObject {

    public Wall(double x, double y, double size) {
        super(x,y,size,size);
    }
    void draw(GraphicsContext gc) {
        gc.drawImage(Assets.wall, getX(), getY(), getW(), getH());
    }
}