package services;

import Driver.Main;
import helpers.HashHelper;
import helpers.StringHelper;
import server.ClientConnection;

import java.sql.*;
import java.util.Base64;

public class UserDbService {

    public static String getUser(Object[] credentials, ClientConnection clientConnection) {
        String username = (String)credentials[0];
        String password = (String)credentials[1];
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

                String hashPassword = rs.getString("password");
                String[] hash_password = hashPassword.split(":");
                byte[] salt = Base64.getDecoder().decode(hash_password[0]);
                if(HashHelper.hashPassword(password, salt).equals(hash_password[1])) {
                    clientConnection.setClientId(id);
                    return row;
                }
                else return "-101";
            }
            else {
                return "-100";
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public static String insert(Object[] values) {
        Connection conn = Main.conn;

        String username = (String)values[0];
        String query = "SELECT EXISTS (SELECT 1 FROM \"user\" WHERE username = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getBoolean(1)) {
                return "-200";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String password = (String)values[1];

        byte[] salt = HashHelper.generateSalt();
        String saltString = Base64.getEncoder().encodeToString(salt);

        password = HashHelper.hashPassword(password, salt);
        password = saltString + ":" + password;

        String sql = "INSERT INTO \"user\" (username, password) VALUES(?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the insert statement
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated key
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return Long.toString(rs.getLong(1));
                    }
                }
            } else {
                return "-1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "-1";
        }
        return "0";
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
