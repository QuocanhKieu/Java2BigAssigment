package moviebookingapp.singleton;

import javafx.scene.layout.GridPane;
import moviebookingapp.entity.BookingReservation;

import java.util.HashSet;
import java.util.Set;

public class ReservationManager {
    private static ReservationManager instance;
    private final Set<BookingReservation> bookingReservationList;
    private final Set<GridPane> reservationGridPaneList;

    private ReservationManager() {
        bookingReservationList = new HashSet<>();
        reservationGridPaneList = new HashSet<>();
    }

    public static synchronized ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }

    public void addBookedReservation(BookingReservation reservation) {
        int isExist = 0;
        for(BookingReservation item: bookingReservationList) {
            if(item.getSeat_id() == reservation.getSeat_id()) isExist = 1;
        }
        if(isExist == 0) bookingReservationList.add(reservation);
    }

    public void removeBookedReservation(BookingReservation reservation) {
        bookingReservationList.remove(reservation);
    }

    public void addGridPane(GridPane gridPane) {
        int isExist = 0;
        for(GridPane item: reservationGridPaneList) {
            if(item.getId().equals(gridPane.getId())) isExist = 1;
        }
        if(isExist == 0) reservationGridPaneList.add(gridPane);
    }

    public void removeGridPane(GridPane gridPane) {
        reservationGridPaneList.remove(gridPane);
    }

    // Other methods to update or retrieve data from the lists

    public Set<BookingReservation> getBookedReservationList() {
        return bookingReservationList;
    }

    public Set<GridPane> getReservationGridPaneList() {
        return reservationGridPaneList;
    }
}
