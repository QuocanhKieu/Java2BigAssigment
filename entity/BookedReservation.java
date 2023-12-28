package moviebookingapp.entity;

import java.time.LocalDate;

public class BookedReservation {
    private int movie_id;
    private String movie_title;
    private int seat_id;
    private String seat_name;
    private String age_type;
    private int ticket_price;
    private String cinema;
    private LocalDate date;

    public BookedReservation(int movie_id, String movie_title, int seat_id, String seat_name, String age_type, int ticket_price, String cinema, LocalDate date) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.seat_id = seat_id;
        this.seat_name = seat_name;
        this.age_type = age_type;
        this.ticket_price = ticket_price;
        this.cinema = cinema;
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

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
