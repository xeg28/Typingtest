package services;

import Driver.Main;
import helpers.MessageHelper;
import helpers.StringHelper;

import java.sql.*;
import java.util.ArrayList;

public class TestDbService {
    public static String getAllTests(int userId, int promptId) {
        Connection conn = Main.conn;
        String sql = "Select * From test Where userid = ? And promptid = ?";
        StringBuilder row = new StringBuilder();
        ArrayList<String> rows = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, promptId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int wpm = rs.getInt("wpm");
                Timestamp testTime = rs.getTimestamp("testtime");
                rows.add(StringHelper.serialize(id, userId, promptId, wpm, testTime));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return MessageHelper.createMessage(rows);
    }
}
