package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
    private int port;
    ServerSocket serverSocket;
    public Listener() {
        this.port = 38765;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket socket = serverSocket.accept();
                new ClientConnection(socket).start();
            }
        } catch(IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }


}
