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

    @SneakyThrows
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5000)){
            System.out.println("Server starting...\nWaiting for clients");
            ServerHandler serverHandler = new ServerHandler();
            serverHandler.start();
            while (true) {
                Socket socket = serverSocket.accept();
                serverHandler.addSocket(socket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
