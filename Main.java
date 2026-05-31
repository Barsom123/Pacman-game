import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import javafx.scene.paint.Color;
public class Main extends Application {
    GameMap map;
    public static String currentLoggedInUser = "";
    HashSet<KeyCode> keys = new HashSet<>();
    @Override
    public void start(Stage stage) {
    Loginpage loginPage = new Loginpage();

        // pass a callback to run after login success
        loginPage.displayLoginPage(() -> startGame(stage));
    }
    public void startGame(Stage stage) {
    Score score = new Score();
    Lives lives = new Lives();
    Canvas canvas = new Canvas(608, 720);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setImageSmoothing(false);
    map = new GameMap(score, lives);
    map.load();
    StackPane root = new StackPane(canvas);
    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.setTitle("PacMan");
    stage.show();
    scene.setOnKeyPressed(e -> keys.add(e.getCode()));
    scene.setOnKeyReleased(e -> keys.remove(e.getCode()));
    stage.setOnCloseRequest(e -> {
        map.score.saveHighScoreToDatabase();
    });
    new AnimationTimer() {
        public void handle(long now) {

            handleInput();
            map.update();
            // Clear screen
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Draw score and lives
            gc.setFont(javafx.scene.text.Font.font(24));

            // Score
            gc.setFill(Color.YELLOW);
            gc.fillText("Score: " + score.getScore(), 20, 30);

            // High Score
            gc.setFill(Color.ORANGE);
            gc.fillText("High Score: " + score.getHighScore(), 220, 30);

            // Lives
            gc.setFill(Color.RED);
            gc.fillText("Lives: " + lives.getLives(), 450, 30);

            // Move map slightly down
            gc.save();
            gc.translate(0, 50);
            map.draw(gc);
            gc.restore();
        }
    }.start();
}

    void handleInput() {
        if (keys.contains(KeyCode.R)) map.reset();
        //Block arrow keys if Pacman is dying, or game is over/won
        if (map.pacman.getIsDying() || map.pacman.getIsGameOver() || map.pacman.getIsWon()) {
            return;
        }
        if (keys.contains(KeyCode.UP)) map.pacman.setDirection(0, -2);
        if (keys.contains(KeyCode.DOWN)) map.pacman.setDirection(0, 2);
        if (keys.contains(KeyCode.LEFT)) map.pacman.setDirection(-2, 0);
        if (keys.contains(KeyCode.RIGHT)) map.pacman.setDirection(2, 0);
    }

    public static void main(String[] args) {
        launch();
    }
}