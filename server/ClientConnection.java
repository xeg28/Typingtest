package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

class ClientConnection extends Thread {
    private Socket socket;
    private int clientId;
    public ClientConnection(Socket socket) {
        this.socket = socket;
        try {
            socket.setSoTimeout(600000);
        } catch (SocketException e) {
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

            String text;
            do {
                text = reader.readLine();
                if(text == null) {
                    break;
                }
                String senderIp = socket.getInetAddress().getHostAddress();
                int senderPort = socket.getPort();
            } while(true);

            socket.close();
            System.out.println("Connection closed with " + socket.getRemoteSocketAddress());
        } catch(SocketException ex) {
            System.out.println("Lost connection with " + socket.getRemoteSocketAddress());
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
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