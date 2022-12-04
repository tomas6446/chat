package com.chat.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
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
    public static List<ChatRoom> chatRoomList = new ArrayList<>();
    public static List<Connection> connectionList = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        new Server().run();
    }

    public static void broadCastServer(String message) {
        System.out.println(message);
    }

    public static void broadCastTo(String recipient, String message) {
        for (Connection connection : connectionList) {
            if (Objects.equals(connection.getUser().getUsername(), recipient)) {
                connection.sendMessage(message);
            }
        }
    }

    public static void broadCastChatRoom(String chatRoom, String message) {
        for (Connection connection : connectionList) {
            if (connection.getUser().isConnectedToChatRoom()
                    && Objects.equals(connection.getUser().getChatRoomName(), chatRoom)) {
                connection.sendMessage(message);
            }
        }
    }

    public void broadCastEveryone(String message) {
        connectionList.forEach(connection -> {
            connection.sendMessage(message);
        });
    }

    private static void exportConnections() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader file = new FileReader("connections.json")) {
            Object obj = jsonParser.parse(file);
            System.out.println(obj.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void importConnections() {
        JSONObject jsonObject = new JSONObject();
        connectionList.forEach(connection -> {
            jsonObject.put("Username", connection.getUser().getUsername());
            jsonObject.put("Password", connection.getUser().getPassword());
        });
        try (FileWriter file = new FileWriter("connections.json")) {
            file.append(jsonObject.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exportChatRoomList() {

    }

    private static void importChatRoomList() {

    }

    public static void shutDown() {
        if (!serverSocket.isClosed()) {
            try {
                exportConnections();
                exportChatRoomList();
                executorService.shutdown();
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            executorService = Executors.newCachedThreadPool();
            System.out.println("Server starting...\nWaiting for clients");

//            // TODO: import chat room list
//            chatRoomList = new ArrayList<>();
//            // TODO: import connections
            exportConnections();
//            connections = new ArrayList<>();

            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connectionList.add(connection);
                executorService.execute(connection);
            }
        } catch (IOException e) {
            shutDown();
        }
    }
}
