package moviebookingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moviebookingapp.controllers.MovieListController;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/moviecinema.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Movies Cinema");
        primaryStage.setScene(new Scene(root, 1600, 900 ));


        MovieListController movieListController = loader.getController();
        movieListController.setStage( primaryStage);


        primaryStage.show();
    }
}