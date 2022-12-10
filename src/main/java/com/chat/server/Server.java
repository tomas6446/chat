package com.chat.server;

import com.chat.server.client.ClientHandler;
import lombok.Getter;

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
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().run();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server starting...\nWaiting for clients");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client " + socket + " connected to server");
                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);
                new Thread(clientHandler).run();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
