package moviebookingapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowTime {

    private int showtime_id;

    private int movie_id;

    private int theater_id;

    private LocalDateTime start_time;

    private LocalDateTime end_time;

    private LocalDate date;

    private ArrayList<Seat> seatlist;

    public ShowTime(int showtime_id, int movie_id, int theater_id, LocalDateTime start_time, LocalDateTime end_time, LocalDate date, ArrayList<Seat> seatlist) {
        this.showtime_id = showtime_id;
        this.movie_id = movie_id;
        this.theater_id = theater_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
        this.seatlist = seatlist;
    }

    public int getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(int showtime_id) {
        this.showtime_id = showtime_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(int theater_id) {
        this.theater_id = theater_id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShowTime{" +
                "showtime_id=" + showtime_id +
                ", movie_id=" + movie_id +
                ", theater_id=" + theater_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", date=" + date +
                '}';
    }
    public void setSeatlist(ArrayList<Seat> seatlist) {
        this.seatlist = seatlist;
    }

    public ArrayList<Seat> getSeatlist() {
        return seatlist;
    }


    public int getTotalSeatQty() {
        int validSeatCout = 0;
        for(Seat seat: seatlist) {
            if(1 == seat.getAvailability_status())
                validSeatCout++;
        }
        return validSeatCout;
    }
    public int getValidSeatQty() {
        int validSeatCout = 0;
        for(Seat seat: seatlist) {

            if(1 == seat.getAvailability_status()) {
                validSeatCout++;
            }
        }
        return validSeatCout;
    }
}


