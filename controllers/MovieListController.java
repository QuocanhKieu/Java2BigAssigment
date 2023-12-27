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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import moviebookingapp.dao.MovieDAO;
import moviebookingapp.entity.Movie;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieListController implements Initializable {
    private Stage currentStage;


    @FXML
    private GridPane moviesGrid; // Inject the GridPane from FXML
    @FXML
    private Pane pane; // Inject the GridPane from FXML


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        imageView.setFitWidth(130); // Adjust the width of the image as needed
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

        return moviePane;
    }

    public void setStage(Stage stage) {

        currentStage = stage;
    }

}
