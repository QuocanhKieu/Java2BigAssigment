package moviebookingapp.entity;

public class Seat {
    private int seat_id;
    private int theater_id;
    private int showtime_id;
    private int  seat_number;
    private int availability_status;

    public Seat(int seat_id, int theater_id, int showtime_id, int seat_number, int availability_status) {
        this.seat_id = seat_id;
        this.theater_id = theater_id;
        this.showtime_id = showtime_id;
        this.seat_number = seat_number;
        this.availability_status = availability_status;
    }

    public int getAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(int availability_status) {
        this.availability_status = availability_status;
    }

    public int getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(int seat_number) {
        this.seat_number = seat_number;
    }

    public int getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(int showtime_id) {
        this.showtime_id = showtime_id;
    }

    public int getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(int theater_id) {
        this.theater_id = theater_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }
}
