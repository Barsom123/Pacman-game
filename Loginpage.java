import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Loginpage {

    public void displayLoginPage(Runnable onLoginSuccess) {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        VBox vbox = new VBox(12);
        vbox.setMaxWidth(250);
        vbox.setStyle("-fx-alignment: center;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        Button goToRegisterButton = new Button("Create New Account");
        goToRegisterButton.setMaxWidth(Double.MAX_VALUE);
        goToRegisterButton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

        Label message = new Label();
        message.setTextFill(Color.RED);
        message.setWrapText(true);

        vbox.getChildren().addAll(
                usernameField,
                passwordField,
                loginButton,
                goToRegisterButton,
                message
        );

        root.getChildren().add(vbox);
        Scene scene = new Scene(root, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                message.setTextFill(Color.RED);
                message.setText("Please fill all fields!");
                return;
            }

            if (DatabaseHelper.validateLogin(username, password)) {
                Main.currentLoggedInUser = username;
                stage.close();
                onLoginSuccess.run();
            } else {
                message.setTextFill(Color.RED);
                message.setText("Wrong username or password!");
            }
        });

        goToRegisterButton.setOnAction(e -> {
            stage.close();
            Registerpage registerPage = new Registerpage();
            registerPage.displayRegisterPage(() -> {
                this.displayLoginPage(onLoginSuccess);
            });
        });
    }
}