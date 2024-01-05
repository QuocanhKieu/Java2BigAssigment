package moviebookingapp.dao;

import moviebookingapp.database.Connector;
import moviebookingapp.entity.Seat;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InputStreamDAO {
    public InputStream findOne(String report_name)
    {
        String sql = "select report_jasper from invoicereport where report_name = ?";
        InputStream inputStr = null;
        try {
            Connector conn = Connector.getInstance();
            PreparedStatement ps = conn.getConn().prepareStatement(sql);
            ps.setString(1, report_name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                inputStr = rs.getBinaryStream("report_jasper");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //đóng 3 kết nối d
        }
        return inputStr;
    }
}
