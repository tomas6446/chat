package com.chat.server;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
public class Server implements Runnable {
    private final List<Handler> connections = new ArrayList<>();
    private ServerSocket serverSocket;

    @SneakyThrows
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server starting...\nWaiting for clients");

            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(this, socket);
                Thread thread = new Thread(handler);
                connections.add(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            serverSocket.close();
        }
    }
}
