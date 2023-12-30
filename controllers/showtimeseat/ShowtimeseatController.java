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
import moviebookingapp.dao.SeatDAO;
import moviebookingapp.dao.ShowTimeDAO;
import moviebookingapp.entity.BookedReservation;
import moviebookingapp.entity.Movie;
import moviebookingapp.entity.Seat;
import moviebookingapp.entity.ShowTime;
import moviebookingapp.singleton.ReservationManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShowtimeseatController implements Initializable {
    private int ADULT_PRICE = 115000;
    private int CHILD_PRICE = 100000;

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
// ko cho đc ra ngoài này

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

            // chô này cug đc nhưng cho ra hẳn ngoài
            Set<Integer> button_clicked = new HashSet<>();

            for (ShowTime showtime : showtimeList) {
                GridPane gridpane = createShowTimeGridPane(showtime);
;               gridpane.setId(showtime.getShowtime_id()+"");
                gridpane.setOnMouseClicked(event-> {

                    //re render the reservation side when get back to the scene
                    ReservationManager reMa_1 = ReservationManager.getInstance();
                    VBox re_content_1= new VBox();
                    re_content_1.getChildren().addAll(reMa_1.getReservationGridPaneList());
                    bookedReservationPane.setContent(re_content_1);


                    
                    List<Seat> seat_list = new SeatDAO().list(showtime.getShowtime_id());
                    System.out.println(seat_list.size());

                    gridPaneFSeat.getChildren().clear();
                    gridPaneFSeat.setGridLinesVisible(true);
                    char[] alphabet = "ABCDE".toCharArray(); // A, B, C, D, E


                    if(showtime.getTotalSeatQty() != 0) {
                        int count = 0;
                        for (int row = 0; row < 5; row++) {
                            for (int col = 0; col < 10; col++) {
                                Seat curent_seat = seat_list.get(count);

                                Button button = new Button(alphabet[row]+ "" + (col + 1));
                                button.setStyle("-fx-background-color:  #D9D9D9;");

                                button.setId(curent_seat.getSeat_id()+"");//setId for each Btn = each seat Id

                                if(curent_seat.getAvailability_status() == 0) {
                                    button.setStyle("-fx-background-color:  #D73F0F;");
                                    button.setDisable(true);
                                }

                                if(curent_seat.getAvailability_status() == 1) {
                                    button.setStyle("-fx-background-color:  #D9D9D9;");
                                    button.setDisable(false);
                                }

                                if(curent_seat.getAvailability_status() == 2) {
                                    button.setStyle("-fx-background-color:  #00b5ef;");
                                    button.setDisable(false);
                                }

                                button.setOnAction((e) -> {
                                    button_clicked.add(Integer.parseInt(button.getId()));

                                    if(curent_seat.getAvailability_status() == 1) {
                                        button.setStyle("-fx-background-color:  #00b5ef;");
                                        curent_seat.setAvailability_status(2);
                                        new SeatDAO().update(curent_seat);

                                        addBookedAndPane(button, showtime);

                                        ReservationManager reMa = ReservationManager.getInstance();
                                        VBox re_content= new VBox();
                                        re_content.getChildren().addAll(reMa.getReservationGridPaneList());
                                        bookedReservationPane.setContent(re_content);

                                    }

                                    else if(curent_seat.getAvailability_status() == 2) { // dùng else if để nó chỉ chạy 1 block thay vì cả 2 dấn tới ko có j thay đổi
                                        button.setStyle("-fx-background-color:  #D9D9D9;");
                                        curent_seat.setAvailability_status(1);
                                        new SeatDAO().update(curent_seat);

                                        removeBookedAndPane(button);

                                        ReservationManager reMa = ReservationManager.getInstance();
                                        VBox re_content= new VBox();
                                        re_content.getChildren().addAll(reMa.getReservationGridPaneList());
                                        bookedReservationPane.setContent(re_content);
                                    }
                                });
                                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Button takes full cell size
                                button.setAlignment(Pos.CENTER);
                                button.setTextAlignment(TextAlignment.CENTER);

                                gridPaneFSeat.setRowIndex(button, row);
                                gridPaneFSeat.setColumnIndex(button, col);
                                gridPaneFSeat.getChildren().add(button);

                                System.out.println(button.getId());
                                count++;
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

    public void removeBookedAndPane(Button button) {
        int button_id = Integer.parseInt(button.getId());
        ReservationManager reMa = ReservationManager.getInstance();
        reMa.removeGridPane(reMa.getReservationGridPaneList().stream().filter((item) -> {
            if(item.getId().equals(button_id+"")) return true;
            else return false;
        } ).findFirst().orElseThrow(() -> new IllegalStateException("GridPane not present")));//lấy gridPane ra khỏi Optional<GridPane> list

        reMa.removeBookedReservation(reMa.getBookedReservationList().stream().filter((item)->{
            if (item.getSeat_id() == button_id) return true;
            else return false;
        }).findFirst().orElseThrow(() -> new IllegalStateException("BookedReservation not present")));;//lấy BookedReservation ra khỏi Optional<BookedReservation> list
    }

    public void addBookedAndPane(Button button, ShowTime showtime) {

        ReservationManager reMa = ReservationManager.getInstance();
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
        reMa.addBookedReservation(re);

        GridPane aGridpane = createReGridPane(re);
        aGridpane.setId(button.getId());
        reMa.addGridPane(aGridpane);
    }
}
