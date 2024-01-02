package moviebookingapp.dao;

import moviebookingapp.database.Connector;
import moviebookingapp.entity.BookingReservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingReservationDAO implements DAOInterface<BookingReservation>{
    @Override
    public ArrayList<BookingReservation> list() {
        String sql = "select * from booking_reservation_tb";
        ArrayList<BookingReservation> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.getConn().createStatement().executeQuery(sql);
            while (rs.next()){
                ls.add(new BookingReservation(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("seat_id"),
                        rs.getString("seat_name"),
                        rs.getString("age_type"),
                        rs.getInt("ticket_price"),
                        rs.getInt("theater_id"),
                        rs.getInt("showtime_id"),
                        LocalDate.parse(rs.getString("date"))
                ));
            }
        }catch (Exception e){
        }
        return ls;
    }

    @Override
    public boolean create(BookingReservation s) {
        try{
            String sql = "insert into booking_reservation_tb(movie_id,movie_title,seat_id,seat_name,age_type,ticket_price,theater_id,showtime_id,date) values(?,?,?,?,?,?,?,?,?)";
            Connector conn = Connector.getInstance();
            PreparedStatement pstm = conn.getConn().prepareStatement(sql);
            pstm.setString(1,s.getMovie_id()+"");
            pstm.setString(2,s.getMovie_title());
            pstm.setString(3,s.getSeat_id()+"");
            pstm.setString(4,s.getSeat_name());
            pstm.setString(5,s.getAge_type());
            pstm.setString(6,s.getTicket_price()+"");
            pstm.setString(7,s.getTheater_id()+"");
            pstm.setString(8,s.getShowtime_id()+"");
            pstm.setString(9,s.getDate().toString());
            pstm.execute();
        }catch (Exception e){
            return false;
        }
        return true;

    }

    @Override
    public boolean update(BookingReservation s) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            String sql = "DELETE FROM booking_reservation_tb WHERE seat_id = ?";
            Connector conn = Connector.getInstance();
            PreparedStatement pstm = conn.getConn().prepareStatement(sql);
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();

            // Check if any rows were affected
            if (rowsAffected > 0) {
                return true; // Deletion successful
            }
        } catch (Exception e) {
            // Handle exceptions here if needed
            e.printStackTrace();
        }
        return false; // Deletion unsuccessful
    }


    @Override
    public BookingReservation findOne(int id) {
        try {
            String sql = "SELECT * FROM booking_reservation_tb WHERE seat_id = ?";
            Connector conn = Connector.getInstance();
            PreparedStatement pstm = conn.getConn().prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return new BookingReservation(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("seat_id"),
                        rs.getString("seat_name"),
                        rs.getString("age_type"),
                        rs.getInt("ticket_price"),
                        rs.getInt("theater_id"),
                        rs.getInt("showtime_id"),
                        LocalDate.parse(rs.getString("date"))
                );
            }
        } catch (Exception e) {
            // Handle exceptions here if needed
            e.printStackTrace();
        }
        return null; // No record found or an error occurred
    }

}
