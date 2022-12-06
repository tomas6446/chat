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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tomas Kozakas
 */
@Getter
public class Server implements Runnable {
    private final List<Connection> connections = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, ChatRoom> chatRooms = new HashMap<>();

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean done;

    public void broadCastServer(String message) {
        System.out.println(message);
    }

    public void broadCastTo(String sender, String recipient, String message) throws IOException {
        if (recipient != null && message != null) {
            User recipientUser = users.get(recipient);
            User senderUser = users.get(sender);
            if (recipientUser.getUsername().equals(recipient)) {
                if (senderUser.isConnectedToPrivateMessages() && recipientUser.isConnectedToPrivateMessages()) {
                    getConnection(recipient).sendMessage(message);
                    getConnection(sender).sendMessage(message);
                } else {
                    getConnection(sender).sendMessage(message);
                }
                recipientUser.addMessage(sender, message);
                senderUser.addMessage(recipient, message);
                exportData();
            }

        }
    }


    public void broadCastChatRoom(String chatRoom, String message) {
        if (message != null && chatRoom != null) {
            connections.forEach(con -> {
                User user = con.getUser();
                if (user.isConnectedToChatRoom()) {
                    con.sendMessage(message);
                }
            });
        }
    }

    public void exportData() throws IOException {
        List<User> userList = users.values().stream().toList();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileWriter("data/users.json"), userList);

        List<ChatRoom> chatRoomList = chatRooms.values().stream().toList();
        objectMapper.writeValue(new FileWriter("data/chatroom.json"), chatRoomList);
    }

    public void importData() throws IOException {
        List<User> userList = new ObjectMapper().readValue(new File("data/users.json"), new TypeReference<>() {
        });
        users = userList.stream().collect(Collectors.toMap(User::getUsername, Function.identity()));

        List<ChatRoom> chatRoomList = new ObjectMapper().readValue(new File("data/chatroom.json"), new TypeReference<>() {
        });
        chatRooms = chatRoomList.stream().collect(Collectors.toMap(ChatRoom::getName, Function.identity()));
    }

    public Connection getConnection(String username) {
        for (Connection con : connections) {
            if (Objects.equals(con.getUser().getUsername(), username)) {
                return con;
            }
        }
        return null;
    }

    public void shutDown() {
        if (serverSocket.isClosed()) {
            done = true;
        }
        try {
            executorService.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            executorService = Executors.newCachedThreadPool();
            System.out.println("Server starting...\nWaiting for clients");

            importData();

            while (!done) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(this, socket);
                connections.add(connection);
                executorService.execute(connection);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            shutDown();
        }
    }
}
