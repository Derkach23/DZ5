package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Set<ServerHandler> clientHandlers;

    public ServerHandler(Socket socket, Set<ServerHandler> clientHandlers) {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Пользователь: " + clientMessage);
                ChatServer.broadcastMessage(clientMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void closeConnection() {
        try {
            ChatServer.removeClient(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
