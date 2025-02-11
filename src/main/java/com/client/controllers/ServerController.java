package com.client.controllers;

import com.client.helpers.CryptoHelper;
import com.client.helpers.QuoteHelper;
import com.client.helpers.UserHelper;

import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ServerController extends Thread{
    private static Socket connection;

    public static PublicKey publicKey;

    public static PrivateKey privateKey;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 38765;
    private static final int RECONNECT_DELAY = 5; // Seconds

    public ServerController() {
        connectToServer();
    }

    public void run() {
        while (true) {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection lost. Attempting to reconnect...");
                if(UserHelper.currentUser != null) UserHelper.currentUser = null;
                reconnect();
            }

            try {
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String message;
                while ((message = reader.readLine()) != null) {
                    RequestController.addResponse(message);
                }
                // If the server closes the connection
                System.out.println("Server disconnected.");
                connection.close();
            } catch (IOException e) {
                System.err.println("Error reading from server: " + e.getMessage());
                if(UserHelper.currentUser != null) UserHelper.resetUser();
                reconnect();
            }
        }
    }

    private static void connectToServer() {
        try {
            connection = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }

    public static void reconnect() {
        // Close existing connection if necessary
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

        // Attempt reconnection
        while (true) {
            try {
                System.out.println("Reconnecting...");
                connection = new Socket(SERVER_HOST, SERVER_PORT);
                System.out.println("Reconnected successfully.");
                if(QuoteHelper.getQuotes() == null) QuoteHelper.updateQuotes();
                break;
            } catch (IOException e) {
                System.err.println("Reconnection failed: " + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_DELAY * 1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // Restore interrupt status
                    System.err.println("Reconnection attempt interrupted.");
                    break;
                }
            }
        }
    }

    public static void sendMessage(String message) {
        OutputStream output = null;
        try {
            output = connection.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(message);

    }

    public static boolean isConnected() {
        if(connection == null) return false;
        return true;
    }
}
