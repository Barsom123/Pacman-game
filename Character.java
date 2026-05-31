public abstract class Character extends GameObject implements Movable {
    private double vx = 0, vy = 0; // velocity in x and y directions
    public Character(double x, double y, double s) {
        super(x, y, s, s);
        setVx(0);
        setVy(0);
    }
    public void setVx(double vx) {
        if (Math.abs(vx) <= GameMap.tile) {
            this.vx = vx;
        }
    }

    public void setVy(double vy) {
        if (Math.abs(vy) <= GameMap.tile) {
            this.vy = vy;
        }
    }
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }
    public void move(GameMap map) {
        setX(getX() + getVx());
        setY(getY() + getVy());
        
        for (int i = 0; i < map.allObjects.size(); i++) {
            GameObject obj = map.allObjects.get(i);
            if (obj instanceof Wall) {
                if (collide(obj)) {
                    setX(getX() - getVx());
                    setY(getY() - getVy());
                    onWallHit();
                    break; 
                }
            }
        }
    }

    void onWallHit() {}
}