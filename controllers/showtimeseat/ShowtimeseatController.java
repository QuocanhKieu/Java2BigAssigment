package moviebookingapp.controllers.showtimeseat;

import javafx.event.ActionEvent;
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
import moviebookingapp.dao.BookingReservationDAO;
import moviebookingapp.dao.InputStreamDAO;
import moviebookingapp.dao.SeatDAO;
import moviebookingapp.dao.ShowTimeDAO;
import moviebookingapp.database.Connector;
import moviebookingapp.entity.BookingReservation;
import moviebookingapp.entity.Movie;
import moviebookingapp.entity.Seat;
import moviebookingapp.entity.ShowTime;
import moviebookingapp.report.Report;
import moviebookingapp.singleton.ReservationManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShowtimeseatController implements Initializable {

    private final int ADULT_PRICE = 115000;
    private final int CHILD_PRICE = 100000;
    private int total_price = 0;
    public Text totalPrice;

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

    private Map map;

//    HashSet<BookedReservation> bookedReservationList = new HashSet<>(); // chung cho tất cả showtime tìm đc
//    List<GridPane> reservationGridPaneList = new ArrayList<>(); // chung cho tất cả showtime tìm đc
// ko cho đc ra ngoài này

    public void setMovieInfo(Movie choosedMovie, Stage currentStage ) {
        
        movie = choosedMovie;
        this.currentStage = currentStage;


        totalPrice.setText("0");
        try {
            ArrayList<ShowTime> showtimeList = new ShowTimeDAO().list(movie);
//            ArrayList<Node> show_time_gridPane = new ArrayList<>();
//            Node existingContent = scrollPane.getContent();
//            System.out.println(existingContent.toString());
//            content.getChildren().add(existingContent);

            // chô này cug đc nhưng cho ra hẳn ngoài
//            Set<Integer> button_clicked = new HashSet<>()
// Tạo lại dữ liệu cho ReservationManager


//re-render the reservation side when get back to the scene
            List<GridPane> reservationGridPaneList = new ArrayList<>();
            List<BookingReservation> bookingReservationList = new BookingReservationDAO().list();
            for(BookingReservation item : bookingReservationList) {
                total_price+=item.getTicket_price();
                GridPane grid_pane = createReGridPane(item);
                grid_pane.setId(item.getSeat_id()+"");
                reservationGridPaneList.add(grid_pane);
            }
            totalPrice.setText(total_price+"");


            VBox re_content_1= new VBox();
            re_content_1.getChildren().addAll(reservationGridPaneList);
            bookedReservationPane.setContent(re_content_1);
//re-render the reservation side when get back to the scene
            VBox content = new VBox(); // for left side - showtime
            for (ShowTime showtime : showtimeList) {
                GridPane gridpane = createShowTimeGridPane(showtime);
;               gridpane.setId(showtime.getShowtime_id()+"");
                gridpane.setOnMouseClicked(event-> {

                    List<Seat> seat_list = new SeatDAO().list(showtime.getShowtime_id());


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
                                    button.setStyle("-fx-background-color:  #ff0000;");
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

//                                    button_clicked.add(Integer.parseInt(button.getId()));

                                    if(curent_seat.getAvailability_status() == 1) {
                                        button.setStyle("-fx-background-color:  #00b5ef;");
                                        curent_seat.setAvailability_status(2);
                                        new SeatDAO().update(curent_seat);

                                        addBookingAndPane(curent_seat, showtime, reservationGridPaneList);

                                        if(adultRadio.isSelected()) {
                                            total_price += ADULT_PRICE;
                                        }else {
                                            total_price += CHILD_PRICE;
                                        }
                                        totalPrice.setText(total_price+"");

                                    }

                                    else if(curent_seat.getAvailability_status() == 2) { // dùng else if để nó chỉ chạy 1 block thay vì cả 2 dấn tới ko có j thay đổi
                                        button.setStyle("-fx-background-color:  #D9D9D9;");
                                        curent_seat.setAvailability_status(1);
                                        new SeatDAO().update(curent_seat);

                                        int booking_amount = new BookingReservationDAO().findOne(curent_seat.getSeat_id()).getTicket_price();
                                            total_price -= booking_amount;
                                        totalPrice.setText(total_price+"");

                                        removeBookingAndPane(curent_seat, reservationGridPaneList);

                                    }
                                });
                                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Button takes full cell size
                                button.setAlignment(Pos.CENTER);
                                button.setTextAlignment(TextAlignment.CENTER);

                                gridPaneFSeat.setRowIndex(button, row);
                                gridPaneFSeat.setColumnIndex(button, col);
                                gridPaneFSeat.getChildren().add(button);

                                count++;
                            }

                        }


                    }
                });
                gridpane.setStyle("-fx-border-color: black; " +
                        "-fx-border-width: 0px 0px 2px 0px; " +
                        "-fx-padding: 10px 0;"); // 10px top and bottom padding
                content.getChildren().add(gridpane);

            }
            scrollPane.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookedReservationPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public GridPane createShowTimeGridPane(ShowTime showtime) {
        GridPane gridPane = new GridPane();
//        gridPane.setHgap(0);
//        gridPane.setVgap(10);

        // Create ColumnConstraints and RowConstraints to distribute space evenly
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPrefWidth(150);
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
        gridPane.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 0px 0px 2px 0px; " +
                "-fx-padding: 10px 0;"); // 10px top and bottom padding
        return gridPane;
    }
    public GridPane createReGridPane(BookingReservation re) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

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
                    label.setText(re.getTicket_price()+" VND");
                }
                GridPane.setRowIndex(label, row);
                GridPane.setColumnIndex(label, col);
                gridPane.getChildren().add(label);
            }
        }

        gridPane.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 0px 0px 2px 0px; " +
                "-fx-padding: 10px 0;"); // 10px top and bottom padding
        return gridPane;
    }
    public void setLightMode(ActionEvent actionEvent) {
    }

    public void setDarkMode(ActionEvent actionEvent) {
    }

    public void backToMovieList(ActionEvent actionEvent) {
        try {
//                System.out.println("in the button");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/moviecinema.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            currentStage.setTitle("Movies Cinema");
            currentStage.setScene(new Scene(root, 1600, 900));

//                System.out.println("here btn Clicked");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoPayment (ActionEvent actionEvent) {
        try{
            new SeatDAO().update();
            new BookingReservationDAO().delete();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/bookingSucess/bookingSucess.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Payment & Invoice");
            currentStage.setScene(new Scene(root, 1600, 900));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeBookingAndPane(Seat seat, List<GridPane> list) {
        int seat_id = seat.getSeat_id();
        new BookingReservationDAO().delete((seat_id));

        try {
            list.remove(list.stream().filter((item) -> {
                if(item.getId().equals(seat_id+"")) return true;
                else return false;
            } ).findFirst().orElseThrow(() -> new IllegalStateException("GridPane not present")));//lấy gridPane ra khỏi Optional<GridPane> list
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox booking_content= new VBox();
        booking_content.getChildren().addAll(list);
        bookedReservationPane.setContent(booking_content);
    }

    public void addBookingAndPane(Seat seat, ShowTime showtime, List<GridPane> list) {
        BookingReservation re = new BookingReservation(
                movie.getId(),
                movie.getTittle(),
                seat.getSeat_id(),
                seat.getSeat_name(),
                adultRadio.isSelected() ? adultRadio.getText() : childRadio.getText(),
                adultRadio.isSelected() ? ADULT_PRICE : CHILD_PRICE,
                showtime.getTheater_id(),
                showtime.getShowtime_id(),
                LocalDate.now()
        );
        new BookingReservationDAO().create(re);// add booking to the db table

        GridPane grid_pane = createReGridPane(re);
        grid_pane.setId(seat.getSeat_id() + "");// quan trong để remove
        grid_pane.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 0px 0px 2px 0px; " +
                "-fx-padding: 10px 0;"); // 10px top and bottom padding

        int isExist = 0;
        for (GridPane item : list) {
            if (item.getId().equals(grid_pane.getId())) {
                isExist = 1;
                break;
            }
        }
        if (isExist == 0) list.add(grid_pane);

        VBox booking_content = new VBox();
        booking_content.getChildren().addAll(list);
        bookedReservationPane.setContent(booking_content);
    }

}
