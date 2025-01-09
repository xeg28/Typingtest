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

    public static String alterUser(Object[] values) {
        Connection conn = Main.conn;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE \"user\" ")
                .append("SET totaltests = ?, averagewpm = ?, bestwpm = ? ")
                .append("WHERE id = ?");
        int userId = Integer.parseInt((String)values[1]);
        int totalTests = Integer.parseInt((String)values[2]);
        double averageWPM = Double.parseDouble((String)values[3]);
        double bestWPM = Double.parseDouble((String)values[4]);
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());
            statement.setInt(1, totalTests);
            statement.setDouble(2, averageWPM);
            statement.setDouble(3, bestWPM);
            statement.setInt(4, userId);
            int value = statement.executeUpdate();
            if(value != 1) return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return "-1";
        }
        return "0";
    }


}
