import javafx.scene.image.Image;

public class Assets {
    public static Image wall = load("/assets/wall.png");
    public static Image redGhost = load("/assets/redGhost.png");
    public static Image blueGhost = load("/assets/blueGhost.png");
    public static Image pinkGhost = load("/assets/pinkGhost.png");
    public static Image orangeGhost = load("/assets/orangeGhost.png");
    public static Image scaredGhost = load("/assets/scaredGhost.png");
    public static Image pacUp = load("/assets/pacmanUp.png");
    public static Image pacDown = load("/assets/pacmanDown.png");
    public static Image pacLeft = load("/assets/pacmanLeft.png");
    public static Image pacRight = load("/assets/pacmanRight.png");
    public static Image food = load("/assets/powerFood.png");
    public static Image cherry = load("/assets/cherry.png");

    private static Image load(String path) {
        return new Image(Assets.class.getResourceAsStream(path));
    }
}