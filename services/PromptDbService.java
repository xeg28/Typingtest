package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Driver.Main;
import helpers.MessageHelper;
import helpers.StringHelper;

public class PromptDbService {

    public static String getAllPrompts() {
        Connection conn = Main.conn;
        String sql = "SELECT * FROM prompt";
        ResultSet result = null;
        StringBuilder response = new StringBuilder();
        ArrayList<String> rows = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while(result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String text = result.getString("text");
                String username = result.getString("username");

                rows.add(StringHelper.serialize(id, title, text, username));
            }
            statement.close();
            return MessageHelper.createMessage(rows);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public static String delete(int promptId, int userId) {
        Connection conn = Main.conn;
        String sql = "SELECT * FROM \"user\" WHERE id = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String username = null;

            if(rs.next()) username = rs.getString("username");
            else return "-402";


            sql = "DELETE FROM prompt WHERE id = ? AND username = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, promptId);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected == 1) {
                return String.valueOf(promptId);
            }
            else {
                return "-401";
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static String insert(Object[] values) {
        Connection conn = Main.conn;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO prompt (title, text, username) ")
                .append("VALUES(?, ?, ?)");
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());
            statement.setString(1, (String)values[1]);
            statement.setString(2, (String)values[2]);
            statement.setString(3, (String)values[3]);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated key
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return Long.toString(rs.getLong(1));
                    }
                }
            } else {
                return Long.toString(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Long.toString(-1);
        }

        return Long.toString(0);
    }


}
