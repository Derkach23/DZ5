package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientHandler implements Runnable {
    private BufferedReader in;

    public ClientHandler(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        String serverMessage;
        try {
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Сервер: " + serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
