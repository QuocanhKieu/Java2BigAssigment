package moviebookingapp.controllers.showtimeseat;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import moviebookingapp.dao.ShowTimeDAO;
import moviebookingapp.entity.Movie;
import moviebookingapp.entity.ShowTime;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowtimeseatController implements Initializable {

    public Pane bookedFilm;
    public Text staffName;
    public ScrollPane scrollPane;
    public RadioButton childRadio;
    public RadioButton adultRadio;
    public ToggleGroup userType;
    public Button paymentBtn;
    public Hyperlink backBtn;
    public ToggleButton lightMode;
    public ToggleButton darkMode;
    public Button logOutBtn;
    public Pane rootPane;

    private Movie movie;

    private Stage currentStage;

    public void setMovieInfo(Movie choosedMovie, Stage currentStage ) {
        movie = choosedMovie;
        this.currentStage = currentStage;

        System.out.println(currentStage);
        System.out.println(movie);
        try {
            if (movie == null) throw new Exception("movie is empty");

            System.out.println(movie.getTittle());
            ShowTimeDAO showTimeDAO = new ShowTimeDAO();

            ArrayList<ShowTime> showtimeList = showTimeDAO.list(movie);

            ArrayList<Node> show_time_gridPane = new ArrayList<>();
            Node existingContent = scrollPane.getContent();
            System.out.println(existingContent.toString());
            VBox content = new VBox();
            content.getChildren().add(existingContent);

            for (ShowTime showtime : showtimeList) {
                content.getChildren().add(createShowTimeGridPane(showtime));
            }

            System.out.println(showtimeList.size());
//        System.out.println("get here");

            scrollPane.setContent(content);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public Node createShowTimeGridPane(ShowTime showtime) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create ColumnConstraints and RowConstraints to distribute space evenly
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(colConstraints, colConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(50);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints);

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                Label label = new Label();
                if(col == 0 && row == 0){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM");
                    label.setText(showtime.getStart_time().format(formatter));
                }
                if(col == 1 && row == 0){
                    label.setText(movie.getTittle());
                }
                if(col == 0 && row == 1){
                    label.setText("Cinema"+ showtime.getTheater_id());
                }
                if(col == 1 && row == 1){
                    label.setText(showtime.getValidSeatQty()+"/"+showtime.getTotalSeatQty());
                }
                GridPane.setRowIndex(label, row);
                GridPane.setColumnIndex(label, col);
                gridPane.getChildren().add(label);


            }
        }
        return gridPane;
    }
    public void setLightMode(ActionEvent actionEvent) {
    }

    public void setDarkMode(ActionEvent actionEvent) {
    }

    public void backToMovieList(ActionEvent actionEvent) {
    }

    public void gotoPayment(ActionEvent actionEvent) {
    }


}
