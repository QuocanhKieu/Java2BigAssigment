package moviebookingapp.dao;

import moviebookingapp.database.Connector;
import moviebookingapp.entity.BookedReservation;
import moviebookingapp.entity.Movie;
import moviebookingapp.entity.Seat;
import moviebookingapp.entity.ShowTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookedReservationDAO implements DAOInterface<BookedReservation>{
    @Override
    public ArrayList<BookedReservation> list() {
        String sql = "select * from bookedReservationTb";
        ArrayList<BookedReservation> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.getConn().createStatement().executeQuery(sql);
            while (rs.next()){
                ls.add(new BookedReservation(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("seat_id"),
                        rs.getString("seat_name"),
                        rs.getString("age_type"),
                        rs.getInt("ticket_price"),
                        rs.getString("cinema"),
                        LocalDate.parse(rs.getString("date"))
                ));
            }
        }catch (Exception e){
        }
        return ls;
    }

    @Override
    public boolean create(BookedReservation s) {
        try{
            String sql = "insert into bookedReservationTb(movie_id,movie_title,seat_id,seat_name,age_type,ticket_price,cinema,date) values(?,?,?,?,?,?,?,?)";
            Connector conn = Connector.getInstance();
            PreparedStatement pstm = conn.getConn().prepareStatement(sql);
            pstm.setString(1,s.getMovie_id()+"");
            pstm.setString(2,s.getMovie_title());
            pstm.setString(3,s.getSeat_id()+"");
            pstm.setString(4,s.getSeat_name());
            pstm.setString(5,s.getAge_type());
            pstm.setString(6,s.getTicket_price()+"");
            pstm.setString(7,s.getCinema());
            pstm.setString(8,s.getDate().toString());
            pstm.execute();
        }catch (Exception e){
            return false;
        }
        return true;

    }

    @Override
    public boolean update(BookedReservation s) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public BookedReservation findOne(int id) {
        return null;
    }
}
