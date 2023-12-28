package moviebookingapp.controllers.showtimeseat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import moviebookingapp.dao.ShowTimeDAO;
import moviebookingapp.entity.BookedReservation;
import moviebookingapp.entity.Movie;
import moviebookingapp.entity.ShowTime;
import moviebookingapp.singleton.ReservationManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ShowtimeseatController implements Initializable {
    int ADULT_PRICE = 115000;
    int CHILD_PRICE = 100000;

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
    public GridPane gridPaneFSeat;
    public ScrollPane bookedReservationPane;
    private Movie movie;
    private Stage currentStage;

//    HashSet<BookedReservation> bookedReservationList = new HashSet<>(); // chung cho tất cả showtime tìm đc
//    List<GridPane> reservationGridPaneList = new ArrayList<>(); // chung cho tất cả showtime tìm đc

    public void setMovieInfo(Movie choosedMovie, Stage currentStage ) {
        movie = choosedMovie;
        this.currentStage = currentStage;

        System.out.println(currentStage);
        System.out.println(movie);
        try {
            System.out.println(movie.getTittle());
            ShowTimeDAO showTimeDAO = new ShowTimeDAO();

            ArrayList<ShowTime> showtimeList = showTimeDAO.list(movie);

            ArrayList<Node> show_time_gridPane = new ArrayList<>();
            Node existingContent = scrollPane.getContent();
            System.out.println(existingContent.toString());
            VBox content = new VBox();
            content.getChildren().add(existingContent);

//            HashSet<BookedReservation> bookedReservationList = new HashSet<>(); // chung cho tất cả showtime tìm đc
//            List<GridPane> reservationGridPaneList = new ArrayList<>(); // chung cho tất cả showtime tìm đc


            for (ShowTime showtime : showtimeList) {
                GridPane gridpane = createShowTimeGridPane(showtime);
;
                gridpane.setOnMouseClicked(event-> {
                    gridPaneFSeat.getChildren().clear();
                    gridPaneFSeat.setGridLinesVisible(true);
                    char[] alphabet = "ABCDE".toCharArray(); // A, B, C, D, E


                    if(showtime.getTotalSeatQty() != 0) {
                        int count = 0;
                        for (int row = 0; row < 5; row++) {
                            for (int col = 0; col < 10; col++) {



                                Button button = new Button(alphabet[row]+ "" + (col + 1));
                                button.setStyle("-fx-background-color:  #D9D9D9;");

                                button.setId(showtime.getSeatlist().get(count).getSeat_id()+"");//setId for each Btn = each seat Id

                                if(showtime.getSeatlist().get(count).getAvailability_status() == 0) {
                                    button.setStyle("-fx-background-color:  #D73F0F;");
                                    button.setDisable(true);
                                }
                                if(showtime.getSeatlist().get(count).getAvailability_status() == 1) {
                                    button.setStyle("-fx-background-color:  #D9D9D9;");
                                    button.setDisable(false);
//                                    EventHandler<ActionEvent> buttonHandler = new EventHandler<>() {
//                                        @Override
//                                        public void handle(ActionEvent event) {
////                                        "như nhau"
//                                        }
//                                    };
//
//                                    EventHandler<ActionEvent> buttonHandler1 = (e)->{
////                                        "như nhau"
////                                        "áp dụng khi interface chỉ có 1 method -> tạo Object từ interface via lamda Ex"
//                                    };
                                    int[] clickCount = {0};

//                                    if(showtime.getSeatlist().get(Integer.parseInt(button.getId())).getiIsSelected())
//                                    {
//                                        button.setStyle("-fx-background-color:  #00b5ef;"); // blue
//                                        clickCount[0]++;
//                                    }

                                    button.setOnAction((e) -> {

                                        clickCount[0]++;
                                        if (clickCount[0] % 2 == 1) {

                                            System.out.println("enter 109");

                                            button.setStyle("-fx-background-color:  #00b5ef;"); // blue
                                            BookedReservation re = new BookedReservation(
                                                    movie.getId(),
                                                    movie.getTittle(),
                                                    Integer.parseInt(button.getId()),
                                                    button.getText(),
                                                    adultRadio.isSelected()?adultRadio.getText():childRadio.getText(),
                                                    adultRadio.isSelected()?ADULT_PRICE:CHILD_PRICE,
                                                    "Cinema" + showtime.getTheater_id(),
                                                    LocalDate.now()
                                            );

                                            ReservationManager reMa = ReservationManager.getInstance();
                                            reMa.addBookedReservation(re);

                                            GridPane aGridpane = createReGridPane(re);
                                            aGridpane.setId(button.getId());
                                            reMa.addGridPane(aGridpane);

                                            VBox reContent= new VBox();

                                            reContent.getChildren().addAll(reMa.getReservationGridPaneList());
                                            bookedReservationPane.setContent(reContent);

                                            showtime.getSeatlist().get(Integer.parseInt(button.getId())).setIsSelected(true);

                                        } else {
                                            clickCount[0] = 0;
                                            button.setStyle("-fx-background-color:  #D9D9D9;"); // grey
                                            showtime.getSeatlist().get(Integer.parseInt(button.getId())).setIsSelected(false);
                                            ReservationManager reMa = ReservationManager.getInstance();
                                            reMa.removeBookedReservation(reMa.getBookedReservationList().get(Integer.parseInt(button.getId())));
                                            reMa.removeGridPane(reMa.getReservationGridPaneList().get(Integer.parseInt(button.getId())));


                                            VBox reContent= new VBox();

                                            reContent.getChildren().addAll(reMa.getReservationGridPaneList());
                                            bookedReservationPane.setContent(reContent);


                                        }
                                    });


                                }



                                count++;
                                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Button takes full cell size
                                button.setAlignment(Pos.CENTER);
                                button.setTextAlignment(TextAlignment.CENTER);
                                // You can set properties to the button if needed


                                gridPaneFSeat.setRowIndex(button, row);
                                gridPaneFSeat.setColumnIndex(button, col);
                                gridPaneFSeat.getChildren().add(button);
                                System.out.println(button.getId());
                            }

                        }

                        System.out.println(count);
                    }
                });

                content.getChildren().add(gridpane);

            }
            System.out.println(showtimeList.size());
            scrollPane.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public GridPane createShowTimeGridPane(ShowTime showtime) {
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
    public GridPane createReGridPane(BookedReservation re) {
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
                    label.setText(re.getMovie_title());
                }
                if(col == 1 && row == 0){
                    label.setText(re.getSeat_name());
                }
                if(col == 0 && row == 1){
                    label.setText(re.getAge_type());
                }
                if(col == 1 && row == 1){
                    label.setText(re.getTicket_price()+"");
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
        try{
//                System.out.println("in the button");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/moviecinema.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            currentStage.setTitle("Movies Cinema");
            currentStage.setScene(new Scene(root, 1600, 900));

//                System.out.println("here btn Clicked");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void gotoPayment(ActionEvent actionEvent) {
    }


}
