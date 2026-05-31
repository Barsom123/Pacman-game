public class BlueGhost extends Ghost {
    public BlueGhost(double x, double y, double s) {
        super(x,y,s, Assets.blueGhost);
    }

    @Override
    void chooseDirection(GameMap map) {
        randomMove();
    }
}