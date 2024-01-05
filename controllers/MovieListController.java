package moviebookingapp.controllers;

import javafx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import moviebookingapp.dao.MovieDAO;
import moviebookingapp.entity.Movie;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieListController implements Initializable {
    public Text staffName;
    private Stage currentStage;


    @FXML
    private GridPane moviesGrid; // Inject the GridPane from FXML
    @FXML
    private Pane pane; // Inject the GridPane from FXML


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffName.setText(LoginFormController.activeUser.getUsername());

        // Fetch movie data from the database using MovieDAO (replace with your DAO logic)
        MovieDAO movieDAO = new MovieDAO();
        ArrayList<Movie> moviesList = movieDAO.list();

        int column = 0;
        int row = 0;

        for (Movie movie : moviesList) {
            Node moviePane = createMoviePane(movie);

            moviesGrid.add(moviePane, column, row);
//            moviesGrid.setGridLinesVisible(true);
//            moviesGrid.setAlignment(Pos.CENTER);
            column++;
            if (column == 4) { // Display 4 movies per row
                column = 0;
                row++;
            }
        }
    }

    private Node createMoviePane(Movie movie) {
        VBox moviePane = new VBox();
        moviePane.setSpacing(5);
//        moviePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        moviePane.setAlignment(Pos.CENTER); // Set alignment to center content within the cell
        // Create and set up the ImageView for movie cover
        ImageView imageView = new ImageView();
        imageView.setFitWidth(170); // Adjust the width of the image as needed
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(200); // Adjust the height of the image as needed
        try {
            // Load the movie image (replace "/path/to/defaultImage.jpg" with a default image path)
            Image image = new Image(movie.getImage_path() != null ? movie.getImage_path() : "./moviebookingapp/asset/film_img/11.jpg");
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use the existing movie button from the Movie class

//        movie.getMovieSelectBtn().setOnAction(event -> handleMovieSelection(movie));

        // Add image view and existing movie button to the VBox

        Label title = new Label(movie.getTittle());

        moviePane.getChildren().addAll(imageView, title, movie.getMovieSelectBtn());

        moviePane.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 10px;");
        return moviePane;
    }

    public void setStage(Stage stage) {

        currentStage = stage;
    }

    public void logout(ActionEvent actionEvent) {
        try {
            // Create a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation Dialog");
            confirmationDialog.setHeaderText("Are you sure you want to logout?");

            // Show the confirmation dialog and wait for user response
            confirmationDialog.showAndWait().ifPresent(response -> {
                try{
                    if (response == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/loginform.fxml"));
                        Parent root = loader.load();
                        currentStage.setTitle("Movies Cinema");
                        currentStage.setScene(new Scene(root, 1600, 900));
                        LoginFormController loginFormController = loader.getController();
                        loginFormController.setStage(currentStage);
                        currentStage.show();

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });



        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
