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

    public static int insert(List<String> column, List<String> value) {
        return 0;
    }


}
