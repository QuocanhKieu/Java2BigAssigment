package moviebookingapp.entity;

import java.time.LocalDate;

public class BookingReservation {
    private int movie_id;
    private String movie_title;
    private int seat_id;
    private String seat_name;
    private String age_type;
    private int ticket_price;
    private int theater_id;
    private int showtime_id;
    private LocalDate date;

    public BookingReservation(int movie_id, String movie_title, int seat_id, String seat_name, String age_type, int ticket_price, int theater_id, int showtime_id , LocalDate date) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.seat_id = seat_id;
        this.seat_name = seat_name;
        this.age_type = age_type;
        this.ticket_price = ticket_price;
        this.theater_id = theater_id;
        this.showtime_id = showtime_id;
        this.date = date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public String getSeat_name() {
        return seat_name;
    }

    public void setSeat_name(String seat_name) {
        this.seat_name = seat_name;
    }

    public String getAge_type() {
        return age_type;
    }

    public void setAge_type(String age_type) {
        this.age_type = age_type;
    }

    public int getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(int ticket_price) {
        this.ticket_price = ticket_price;
    }

    public int getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(int cinema) {
        this.theater_id = cinema;
    }
    public int getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(int showtime_id) {
        this.showtime_id = showtime_id;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
