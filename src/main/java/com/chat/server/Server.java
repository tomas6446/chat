package com.chat.server;

import com.chat.model.ChatRoom;
import com.chat.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
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
@Getter
public class Server implements Runnable {

    private List<ChatRoom> chatRoomList = new ArrayList<>();
    private List<Connection> connectionList = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public void broadCastServer(String message) {
        System.out.println(message);
    }

    public void broadCastTo(String recipient, String message) {
        for (Connection connection : connectionList) {
            if (Objects.equals(connection.getUser().getUsername(), recipient)) {
                connection.sendMessage(message);
            }
        }
    }

    public void broadCastChatRoom(String chatRoom, String message) {
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

    public void exportConnections() throws IOException {
//        Set<User> users = connectionList.stream().map(Connection::getUser).collect(Collectors.toSet());
//
//        // Save new not existing users
//        List<User> newUsers = users.stream().filter(Objects::nonNull).filter(i -> !usersList.contains(i)).collect(Collectors.toList());
//        usersList.addAll(newUsers);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileWriter("connections.json"), usersList);
    }

    public void importConnections() throws IOException {
        List<User> readValue = new ObjectMapper().readValue(new File("connections.json"), new TypeReference<>() {
        });
        this.usersList = readValue;
    }

    private void exportChatRoomList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileWriter("chatroom.json"), chatRoomList);
    }

    private void importChatRoomList() throws IOException {
        List<ChatRoom> readValue = new ObjectMapper().readValue(new File("chatroom.json"), new TypeReference<>() {
        });
        this.chatRoomList = readValue;
    }

    public void shutDown() {
        if (serverSocket.isClosed()) {
            return;
        }
        try {
            exportConnections();
            exportChatRoomList();
            executorService.shutdown();
            serverSocket.close();
        } catch (IOException ignored) {
            System.err.println(ignored.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            executorService = Executors.newCachedThreadPool();
            System.out.println("Server starting...\nWaiting for clients");

            importConnections();
            importChatRoomList();

            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(this, socket);
                connectionList.add(connection);
                executorService.execute(connection);
            }
        } catch (IOException e) {
            shutDown();
        }
    }
}
