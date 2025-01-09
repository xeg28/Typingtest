package Driver;

import helpers.MessageHelper;
import helpers.StringHelper;
import io.github.cdimascio.dotenv.Dotenv;
import server.Listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection conn;
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // Loads the .env file

        String dbUser = dotenv.get("DB_USER"); // Example: Retrieve a value
        String dbPass = dotenv.get("DB_PASSWORD");
        String dbHost = dotenv.get("DB_HOST");
        String dbPort = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_NAME");
        String dbURL = "jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbName;

        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        new Listener().start();

    }
}