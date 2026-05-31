public class RedGhost extends Ghost {
    public RedGhost(double x, double y, double s) {
        super(x,y,s, Assets.redGhost);
    }

 @Override
    void chooseDirection(GameMap map) {
        randomMove();
    }
}
