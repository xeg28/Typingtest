package server;

import Driver.Main;
import helpers.MessageHelper;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.PublicKey;
import java.util.Base64;

public class ClientConnection extends Thread {
    private Socket socket;
    private int clientId;

    public PublicKey clientPublicKey;
    public ClientConnection(Socket socket) {
        this.socket = socket;
        try {
            socket.setSoTimeout(600000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void sendPublicKey() {
        OutputStream output = null;
        PublicKey publicKey = Main.publicKey;
        try {
            output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            writer.println("000 " + publicKeyString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a buffered reader from the sockets input stream
     * which will be used to listen for messages that arrive from this connection.
     * If the message is null, then the connection has been closed. This method will
     * also print the message when it is received.
     */
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            String text;
            do {
                text = reader.readLine();
                if(text == null) {
                    break;
                }
                System.out.println(text);
                String response = MessageHelper.getResponse(text, this);
                if(response != null && response.equals("000")) continue;
                if(response != null) {
                    writer.println(response);
                }

            } while(true);

            socket.close();
            System.out.println("Connection closed with " + socket.getRemoteSocketAddress());
        } catch(SocketException ex) {
            System.out.println("Lost connection with " + socket.getRemoteSocketAddress());
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch(IOException e) {
                System.out.println("Failed to close socket.");
                e.printStackTrace();
            }
        }
    }

}