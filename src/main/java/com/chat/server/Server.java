package com.chat.server;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
@Getter
public class Server implements Runnable {
    private ServerSocket serverSocket;

    @SneakyThrows
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server starting...\nWaiting for clients");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client is connected: " + socket);
                new Handler(socket).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            serverSocket.close();
        }
    }
}
