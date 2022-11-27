package com.chat.server;

import com.chat.client.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class Server {
    public static List<ClientHandler> activeUsers = new ArrayList<>();

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for clients ...");

            do {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("A new client is connected: " + socket);

                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                    System.out.println("Received: " + activeUsers.get(activeUsers.size() - 1).toString());
                    ClientHandler clientHandler = new ClientHandler((User) objectInputStream.readObject(), socket, objectInputStream, objectOutputStream);
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                } catch (Exception e) {
                    socket.close();
                }
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printActiveUsers() {
        activeUsers.forEach(System.out::println);
    }

    public static void main(String[] args) {
        new Server(5000);
    }
}
