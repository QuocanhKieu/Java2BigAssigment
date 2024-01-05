package moviebookingapp.dao;

import moviebookingapp.database.Connector;
import moviebookingapp.entity.Seat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO implements DAOInterface<Seat>{
    @Override
    public ArrayList<Seat> list() {
        String sql = "select * from seats";
        ArrayList<Seat> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);
//            ps.setInt(1,showTime_Id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ls.add(new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("theater_id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_name"),
                        rs.getInt("availability_status")
                ));
            }
        }catch (Exception e){
        }
        return ls;
    }


    public ArrayList<Seat> list(int showTime_Id) {
        String sql = "select * from seats where showtime_id = ?";
        ArrayList<Seat> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);
            ps.setInt(1,showTime_Id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ls.add(new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("theater_id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_name"),
                        rs.getInt("availability_status")
                ));
            }
        }catch (Exception e){
        }
        return ls;
    }
    @Override
    public boolean create(Seat s) {
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
    public boolean update(Seat s) {
        String sql = "UPDATE seats SET theater_id = ?, showtime_id = ?, seat_name = ?, availability_status = ? WHERE seat_id = ?";
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);

            // Set the parameters for the update query
            ps.setInt(1, s.getTheater_id());
            ps.setInt(2, s.getShowtime_id());
            ps.setString(3, s.getSeat_name());
            ps.setInt(4, s.getAvailability_status());
            ps.setInt(5, s.getSeat_id()); // Assuming seatId is the unique identifier

            int rowsUpdated = ps.executeUpdate();

            // Check if any rows were affected by the update
            if (rowsUpdated > 0) {
                return true; // Update successful
            }
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace(); // Print the exception for debugging
        }
        return false; // Update failed
    }

    public boolean update() {
        String sql = "UPDATE seats SET availability_status = 0 WHERE availability_status = 2;";
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);



            int rowsUpdated = ps.executeUpdate();

            // Check if any rows were affected by the update
            if (rowsUpdated > 0) {
                return true; // Update successful
            }
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace(); // Print the exception for debugging
        }
        return false; // Update failed
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Seat findOne(int id) {
        return null;
    }
}
