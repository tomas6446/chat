package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import com.chat.view.ViewHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
public class Client implements Runnable {
    private User user;
    private Chat chat;
    private ViewHandler viewHandler;
    private Message message;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(User user, ViewHandler viewHandler, Message message) {
        this.viewHandler = viewHandler;
        this.user = user;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            try (Socket socket = new Socket("127.0.0.1", 5000)) {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                connect();

                while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                    System.out.println("Listener: " + message);
                    user = message.getUser();
                    chat = message.getChat();
                    switch (message.getMessageType()) {
                        case CONNECTED -> main();
                        case JOINED_ROOM -> chat();
                        case RECEIVE -> System.out.println(message);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not connect to server");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        switch (message.getMessageType()) {
            case LOGIN -> login();
            case REGISTER -> register();
        }
    }

    @SneakyThrows
    private void register() {
        outputStream.writeObject(new Message(user, chat, message.getMessage(), MessageType.REGISTER));
    }

    @SneakyThrows
    private void login() {
        outputStream.writeObject(new Message(user, chat, message.getMessage(), MessageType.LOGIN));
    }

    @SneakyThrows
    public void joinRoom(Chat chat) {
        outputStream.writeObject(new Message(user, chat, MessageType.JOIN_ROOM));
    }

    @SneakyThrows
    public void createRoom(Chat chat) {
        outputStream.writeObject(new Message(user, chat, MessageType.CREATE_ROOM));
    }

    @SneakyThrows
    public void sendMessage(String message) {
        outputStream.writeObject(new Message(user, chat, message, MessageType.SEND));
    }

    @SneakyThrows
    public void auth() {
        viewHandler.launchLoginWindow();
    }

    @SneakyThrows
    public void main() {
        viewHandler.launchMainWindow(this);
    }

    @SneakyThrows
    public void chat() {
        viewHandler.launchChatWindow(this);
    }
}
