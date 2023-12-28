package moviebookingapp.singleton;

import javafx.scene.layout.GridPane;
import moviebookingapp.entity.BookedReservation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationManager {
    private static ReservationManager instance;
    private final List<BookedReservation> bookedReservationList;
    private final List<GridPane> reservationGridPaneList;

    private ReservationManager() {
        bookedReservationList = new ArrayList<>();
        reservationGridPaneList = new ArrayList<>();
    }

    public static synchronized ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }

    public void addBookedReservation(BookedReservation reservation) {
        bookedReservationList.add(reservation);
    }

    public void removeBookedReservation(BookedReservation reservation) {
        bookedReservationList.remove(reservation);
    }

    public void addGridPane(GridPane gridPane) {
        reservationGridPaneList.add(gridPane);
    }

    public void removeGridPane(GridPane gridPane) {
        reservationGridPaneList.remove(gridPane);
    }

    // Other methods to update or retrieve data from the lists

    public List<BookedReservation> getBookedReservationList() {
        return bookedReservationList;
    }

    public List<GridPane> getReservationGridPaneList() {
        return reservationGridPaneList;
    }
}
