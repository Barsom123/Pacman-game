public class PinkGhost extends Ghost {
    public PinkGhost(double x, double y, double s) {
        super(x,y,s, Assets.pinkGhost);
    }

    @Override
    void chooseDirection(GameMap map) {
        randomMove();
    }
}