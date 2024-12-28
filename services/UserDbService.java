package services;

import Driver.Main;
import helpers.StringHelper;

import java.sql.*;

public class UserDbService {

    public static String getUser(String username) {
        Connection conn = Main.conn;
        String sql = "Select * From \"user\" Where username = ?";
        String row = "";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                int totalTests = rs.getInt("totaltests");
                double averageWPM = rs.getDouble("averagewpm");
                int bestWPM = rs.getShort("bestwpm");
                Timestamp dateCreated = rs.getTimestamp("datecreated");
                row = StringHelper.serialize(id, username, totalTests, averageWPM, bestWPM, dateCreated);
            }
            else {
                return null;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return row;
    }
}
