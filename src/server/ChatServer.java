package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ServerHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Запущен чат-сервер:");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerHandler clientHandler = new ServerHandler(clientSocket, clientHandlers);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcastMessage(String message, ServerHandler excludeUser) {
        for (ServerHandler clientHandler : clientHandlers) {
            if (clientHandler != excludeUser) {
                clientHandler.sendMessage(message);
            }
        }
    }

    static void removeClient(ServerHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}
