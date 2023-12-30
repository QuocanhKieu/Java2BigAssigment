package moviebookingapp.dao;

import moviebookingapp.database.Connector;
import moviebookingapp.entity.Seat;
import moviebookingapp.entity.ShowTime;
import moviebookingapp.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShowTimeDAO implements DAOInterface<ShowTime>{
    @Override
    public ArrayList<ShowTime> list() {
        String sql = "select * from showtimes";
        ArrayList<ShowTime> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.getConn().createStatement().executeQuery(sql);
            while (rs.next()){
                SeatDAO seatDAO = new SeatDAO();

                ArrayList<Seat> seatList = seatDAO.list(rs.getInt("showtime_id"));
                ls.add(new ShowTime(
                        rs.getInt("showtime_id"),
                        rs.getInt("movie_id"),
                        rs.getInt("theater_id"),
                        LocalDateTime.parse(rs.getString("start_time")),
                        LocalDateTime.parse(rs.getString("end_time")),
                        LocalDate.parse(rs.getString("date")),
                        seatList
                ));
            }
        }catch (Exception e){
        }
        return ls;
    }

    public ArrayList<ShowTime> list(Movie movie) {
        String sql = "SELECT * FROM `showtimes` WHERE movie_id = ?;";
        ArrayList<ShowTime> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);
            ps.setInt(1, movie.getId()); // Assuming movie.getId() retrieves the movie's ID

            System.out.println("movie_id is"+movie.getId() );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                SeatDAO seatDAO = new SeatDAO();
                ArrayList<Seat> seatList = seatDAO.list(rs.getInt("showtime_id"));
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                ls.add(new ShowTime(
                        rs.getInt("showtime_id"),
                        rs.getInt("movie_id"),
                        rs.getInt("theater_id"),
                        LocalDateTime.parse(rs.getString("start_time"),formatter),
                        LocalDateTime.parse(rs.getString("end_time"),formatter),
                        LocalDate.parse(rs.getString("date")),
                        seatList
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public boolean create(ShowTime s) {
//        try{
//            String sql = "insert into students(fullname,email,telephone,address,dob,class_id) values(?,?,?,?,?,?)";
//            Connector conn = Connector.getInstance();
//            PreparedStatement pstm = conn.getConn().prepareStatement(sql);
//            pstm.setString(1,s.getFullName());
//            pstm.setString(2,s.getEmail());
//            pstm.setString(3,s.getTelephone());
//            pstm.setString(4,s.getAddress());
//            pstm.setString(5,s.getDob().toString());
//            pstm.setString(5,s.getDob().toString());
//            pstm.execute();
//        }catch (Exception e){
//            return false;
//        }
//        return true;

        return true;
    }

    @Override
    public boolean update(ShowTime s) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public ShowTime findOne(int id) {
        return null;
    }
}
