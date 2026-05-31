public class OrangeGhost extends Ghost {
    public OrangeGhost(double x, double y, double s) {
        super(x,y,s, Assets.orangeGhost);
    }

    @Override
    void chooseDirection(GameMap map) {
        randomMove();
    }
}