package com.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tomas Kozakas
 */
public class Server implements Runnable {
    public static List<ChatRoom> chatRoomList= new ArrayList<>();
    public static List<ConnectionHandler> connections = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static ExecutorService executorService;
    private boolean done = false;

    public static void main(String[] args) {
        new Server().run();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            executorService = Executors.newCachedThreadPool();
            System.out.println("Server starting...\nWaiting for clients");

//            // TODO: export chat room list
//            chatRoomList = new ArrayList<>();
//            // TODO: export connections
//            connections = new ArrayList<>();

            while (!done) {
                Socket socket = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(socket);
                connections.add(connectionHandler);
                executorService.execute(connectionHandler);
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public static void broadCastServer(String message) {
        System.out.println(message);
    }

    public void broadCastEveryone(String message) {
        connections.forEach(connection -> {
            connection.sendMessage(message);
        });
    }

    public static void broadCastTo(String recipient, String message) {
        for (ConnectionHandler connection : connections) {
            if (Objects.equals(connection.getUser().getUsername(), recipient)) {
                connection.sendMessage(message);
            }
        }
    }

    public static void broadCastChatRoom(String chatRoom, String message) {
        for (ConnectionHandler connection : connections) {
            if (connection.getUser().isConnectedToChatRoom()
                    && Objects.equals(connection.getUser().getChatRoomName(), chatRoom)) {
                connection.sendMessage(message);
            }
        }
    }

    public static void shutDown() {
        if (!serverSocket.isClosed()) {
            try {
                executorService.shutdown();
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
