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
                        rs.getInt("seat_number"),
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
                        rs.getInt("seat_number"),
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
        return false;
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
