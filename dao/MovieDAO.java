package moviebookingapp.dao;

import moviebookingapp.database.Connector;
//import moviebookingapp.entity.Student;
import moviebookingapp.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovieDAO implements DAOInterface<Movie>{
    @Override
    public ArrayList<Movie> list() {
        String sql = "SELECT * FROM `movies`;";
        ArrayList<Movie> ls = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.getConn().createStatement().executeQuery(sql);
            int count = 0;
            while (rs.next()){
                ls.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("Genre"),
                        LocalDate.parse(rs.getString("release_date")),
                        rs.getString("image_path")

                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public boolean create(Movie s) {
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
    public boolean update(Movie s) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Movie findOne(int id) {
        return null;
    }
}
