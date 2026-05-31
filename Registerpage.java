import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.regex.Pattern;

public class Registerpage {

    public void displayRegisterPage(Runnable onRegistrationSuccess) {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        VBox vbox = new VBox(12);
        vbox.setMaxWidth(250);
        vbox.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerButton = new Button("Register");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        
        Label message = new Label();
        message.setTextFill(Color.RED);
        message.setWrapText(true);

        vbox.getChildren().addAll(
                usernameField,
                emailField,
                passwordField,
                registerButton,
                message
        );

        root.getChildren().add(vbox);
        Scene scene = new Scene(root, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                message.setTextFill(Color.RED);
                message.setText("All fields are required!");
                return;
            }

            if (!isValidEmail(email)) {
                message.setTextFill(Color.RED);
                message.setText("Invalid email address format!");
                return;
            }

            String result = registerUser(username, email, password);

            if (result.equals("SUCCESS")) {
                stage.close();
                onRegistrationSuccess.run();
            } else {
                message.setTextFill(Color.RED);
                message.setText(result);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private String registerUser(String username, String email, String password) {
        if (DatabaseHelper.isDuplicate("email", email)) {
            return "Email is already registered!";
        }
        if (DatabaseHelper.isDuplicate("username", username)) {
            return "Username is already taken!";
        }

        boolean success = DatabaseHelper.insertUser(username, email, password);
        return success ? "SUCCESS" : "Registration failed. Try again.";
    }
}