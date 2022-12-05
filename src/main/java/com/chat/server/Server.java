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
    private final List<Connection> connectionList = new ArrayList<>();
    private List<ChatRoom> chatRoomList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean done;

    public void broadCastServer(String message) {
        System.out.println(message);
    }

    public void broadCastTo(String recipient, String message) {
        if (recipient != null) {
            for (Connection connection : connectionList) {
                if (connection.getUser().isConnectedToPrivateMessages() && Objects.equals(connection.getUser().getUsername(), recipient)) {
                    connection.getUser().addMessage(recipient, message);
                    connection.sendMessage(message);
                }
            }
        }
    }

    public void broadCastChatRoom(String chatRoom, String message) throws IOException {
        if (message != null && chatRoom != null) {
            for (ChatRoom chat : chatRoomList) {
                if (Objects.equals(chat.getName(), chatRoom)) {
                    chat.getMessageList().add(message);
                    exportChatRoom();
                }
            }
            for (Connection connection : connectionList) {
                if (connection.getUser().isConnectedToChatRoom() && Objects.equals(connection.getUser().getChatRoomName(), chatRoom)) {
                    connection.sendMessage(message);
                }
            }
        }
    }

    public void exportUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileWriter("data/users.json"), userList);
    }

    public void importUser() throws IOException {
        userList = new ObjectMapper().readValue(new File("data/users.json"), new TypeReference<>() {
        });
    }

    public void exportChatRoom() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileWriter("data/chatroom.json"), chatRoomList);
    }

    private void importChatRoom() throws IOException {
        chatRoomList = new ObjectMapper().readValue(new File("data/chatroom.json"), new TypeReference<>() {
        });
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

            importUser();
            importChatRoom();

            while (!done) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(this, socket);
                connectionList.add(connection);
                executorService.execute(connection);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            shutDown();
        }
    }
}
