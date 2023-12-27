package moviebookingapp.entity;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import moviebookingapp.controllers.showtimeseat.ShowtimeseatController;

import java.time.LocalDate;

public class Movie {
    private Integer id;
    private String tittle;
    private Integer duration;
    private String genre;
    private LocalDate releaseDate;
    private String image_path;
    private Button movieSelectBtn;

    public Movie(Integer id, String tittle, Integer duration, String genre, LocalDate releaseDate, String image_path) {
        this.id = id;
        this.tittle = tittle;
        this.duration = duration;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.image_path = image_path;
        this.movieSelectBtn = new Button("Chọn Phim");
        movieSelectBtn.setStyle("-fx-background-color: #2D3648; -fx-background-radius: 4 4 4 4;-fx-text-fill: white;");


// For hover state
        movieSelectBtn.setOnMouseEntered(e -> movieSelectBtn.setStyle("-fx-background-color: #404b60; -fx-background-radius: 4 4 4 4;-fx-text-fill: white;-fx-cursor: hand;"));
        movieSelectBtn.setOnMouseExited(e -> movieSelectBtn.setStyle("-fx-background-color: #2D3648; -fx-background-radius: 4 4 4 4;-fx-text-fill: white;"));

// For pressed state
        movieSelectBtn.setOnMousePressed(e -> movieSelectBtn.setStyle("-fx-background-color: #505d77; -fx-background-radius: 4 4 4 4;-fx-text-fill: white;-fx-cursor: hand;"));
        movieSelectBtn.setOnMouseReleased(e -> movieSelectBtn.setStyle("-fx-background-color: #404b60; -fx-background-radius: 4 4 4 4;-fx-text-fill: white;-fx-cursor: hand;"));

        this.movieSelectBtn.setOnAction(event-> {
            try{
//                System.out.println("in the button");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/showtimeseat/showtimeseat.fxml"));
                Parent root = loader.load();
                ShowtimeseatController showtimeseatController = loader.getController();

                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                showtimeseatController.setMovieInfo(this, currentStage);

                currentStage.setTitle("Schedule & Seating");
                currentStage.setScene(new Scene(root, 1600, 900));



//                System.out.println("here btn Clicked");

            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTittle() {
        return this.tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Button getMovieSelectBtn() {
        return this.movieSelectBtn;
    }

    @Override
    public String toString() {
        return tittle;
    }
}
