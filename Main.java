package moviebookingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moviebookingapp.controllers.LoginFormController;
import moviebookingapp.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<User> userpasshash = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        userList.add(new User("user01", "123"));
        userList.add(new User("user02", "456"));
        userList.add(new User("user03", "789"));

        String salt = BCrypt.gensalt(10);

        for (User user : userList) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
            User passhas = new User(user.getUsername(), hashedPassword);
            userpasshash.add(passhas);
        }
        String url = "jdbc:mysql://localhost:3306/movies_booking";
        String username = "root";
        String password = "root";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO logininfover2 (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            for (User user : userpasshash) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginform.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login To Control");
            primaryStage.setScene(new Scene(root, 1600, 900));
            LoginFormController loginFormController = loader.getController();
            loginFormController.setStage(primaryStage);
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}