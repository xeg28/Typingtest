package Driver;

import helpers.CryptoHelper;
import helpers.MessageHelper;
import io.github.cdimascio.dotenv.Dotenv;
import server.ClientConnection;
import server.Listener;

import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection conn;
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    public static void main(String[] args) {

        KeyPair keypair = CryptoHelper.generateKeyPair();
        privateKey = keypair.getPrivate();
        publicKey = keypair.getPublic();

        Dotenv dotenv = Dotenv.load();

        String dbUser = dotenv.get("DB_USER");
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