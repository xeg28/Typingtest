package services;

import Driver.Main;
import helpers.MessageHelper;
import helpers.StringHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDbService {
    public static String getAllTests(int userId, int promptId) {
        Connection conn = Main.conn;
        String sql = "Select * From test Where userid = ? AND promptid = ? ORDER BY wpm DESC";
        StringBuilder row = new StringBuilder();
        ArrayList<String> rows = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, promptId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double wpm = rs.getDouble("wpm");
                Timestamp testTime = rs.getTimestamp("testtime");
                Statement userStatement = conn.createStatement();
                ResultSet userRS = userStatement.executeQuery("select username from \"user\" where id = "+userId);
                String username = "";
                if(userRS.next()) username = userRS.getString("username");
                rows.add(StringHelper.serialize(id, username, promptId, wpm, testTime));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return MessageHelper.createMessage(rows);
    }

    public static String getLeaderboard(int promptId) {
        Connection conn = Main.conn;
        StringBuilder sql = new StringBuilder();
        sql.append("WITH MaxWpm AS (" )
                .append("SELECT userid, MAX(wpm) AS max_wpm ")
                .append("FROM test ")
                .append("WHERE promptid = ? ")
                .append("GROUP BY userid) ")
                .append("SELECT DISTINCT ON (t.userid, t.wpm) t.* ")
                .append("FROM test t ")
                .append("JOIN MaxWpm mw ")
                .append("ON t.userid = mw.userid AND t.wpm = mw.max_wpm ")
                .append("WHERE t.promptid = ? ")
                .append("ORDER BY t.wpm DESC");
        ArrayList<String> rows = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());
            statement.setInt(1, promptId);
            statement.setInt(2, promptId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userid");
                double wpm = rs.getDouble("wpm");
                Timestamp testTime = rs.getTimestamp("testtime");
                String username = "";
                Statement userNameStatement = conn.createStatement();
                ResultSet usernameRS = userNameStatement.executeQuery("SELECT username from \"user\" where id = " + userId);
                if(usernameRS.next()) {
                    username = usernameRS.getString("username");
                }

                rows.add(StringHelper.serialize(id, username, promptId, wpm, testTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return MessageHelper.createMessage(rows);
    }

    public static long insert(List<String> values) {
        Connection conn = Main.conn;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO test (userid, promptid, wpm)")
                    .append("VALUES(?, ? , ?)");
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());
            statement.setInt(1, Integer.parseInt(values.get(0)));
            statement.setInt(2, Integer.parseInt(values.get(1)));
            statement.setDouble(3, Double.parseDouble(values.get(2)));
            // Execute the insert statement
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated key
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }
}
