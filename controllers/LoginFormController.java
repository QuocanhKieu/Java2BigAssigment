package moviebookingapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import moviebookingapp.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFormController implements Initializable {
    public static User activeUser;

    @FXML
    private Button btnok;

    @FXML
    private TextField fxmlname;

    @FXML
    private PasswordField fxmlpass;


    private Stage currentStage;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    void login(ActionEvent event) {
        String user = fxmlname.getText();
        String pass = fxmlpass.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Error!!!", "Fill the blanks");
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_booking", "root", "root");
                pst = con.prepareStatement("select password from logininfover2 where username=?");
                pst.setString(1, user);
                rs = pst.executeQuery();

                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    boolean check = BCrypt.checkpw(pass, hashedPasswordFromDB);
                    if (check) {
                        showAlert("Welcome " + user, "Login Success");
                        activeUser = new User(user, pass);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/moviecinema.fxml"));
                        Parent root = loader.load();
                        currentStage.setTitle("Movies Cinema");
                        currentStage.setScene(new Scene(root, 1600, 900 ));
                        MovieListController movieListController = loader.getController();
                        movieListController.setStage( currentStage);
                        currentStage.show();



//                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("moviecinema.fxml")));
//                        Stage stage = new Stage();
//                        Scene scene = new Scene(root);
//                        stage.setScene(scene);
//                        stage.show();


                    } else {
                        showAlert("Forgot password?", "Login Failed");
                        fxmlname.setText("");
                        fxmlpass.setText("");
                        fxmlname.requestFocus();
                    }
                } else {
                    showAlert("Forgot password?", "Login Failed");
                    fxmlname.setText("");
                    fxmlpass.setText("");
                    fxmlname.requestFocus();
                }
            } catch (ClassNotFoundException | SQLException | IOException e) {
               e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxmlname.setText("");
        fxmlpass.setText("");
    }

    //Alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {

        currentStage = stage;
    }
}
