import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    private double x, y, w, h;// position and width/height
    protected Image image;
    public GameObject(double x, double y, double w, double h) {
        setX(x); 
        setY(y);
        setW(w); 
        setH(h);
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getW() { return w; }
    public double getH() { return h; }
    public void setX(double x) {
        if (x >= 0 && x <= GameMap.mapWidth - this.w) {
            this.x = x;
        }
    }

    public void setY(double y) {
        if (y >= 0 && y <= GameMap.mapHeight - this.h) {
            this.y = y;
        }
    }

    public void setW(double w) {
        if (w > 0 && w <= GameMap.mapWidth) {
            this.w = w;
        }
    }
    public void setH(double h) {
        if (h > 0 && h <= GameMap.mapHeight) {
            this.h = h;
        }
    }
    abstract void draw(GraphicsContext gc);

    boolean collide(GameObject o) {
       return getX() < o.getX() + o.getW() && getX() + getW() > o.getX() &&
           getY() < o.getY() + o.getH() && getY() + getH() > o.getY();
    }
}