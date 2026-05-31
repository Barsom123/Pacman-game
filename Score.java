import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Score {
    private int score = 0;
    private int highScore = 0;
    private boolean isNewHighScore = false; 
    public Score() {
        if (Main.currentLoggedInUser != null && !Main.currentLoggedInUser.isEmpty()) {
            highScore = DatabaseHelper.getHighScore(Main.currentLoggedInUser);
        }
    }
    public void add(int points) { 
        if (points > 0) {
            score += points;
        }
        if (score > highScore) {
            highScore = score;
            isNewHighScore = true; 
        }
    }
    public int getScore() {
        return score;
    }
    public int getHighScore() {
        return highScore;
    }
    public void saveHighScoreToDatabase() {
        if (isNewHighScore && Main.currentLoggedInUser != null && !Main.currentLoggedInUser.isEmpty()) {
            DatabaseHelper.updateHighScore(Main.currentLoggedInUser, highScore);
            isNewHighScore = false; 
        }
    }
    public void resetScore() {
        saveHighScoreToDatabase(); 
        score = 0;
    }           
} 