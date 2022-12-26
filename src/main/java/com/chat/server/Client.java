package com.chat.server;

import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import com.chat.view.impl.ViewHandlerImpl;
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
    private String chatName;
    private String msgReceivedChat;
    private String msg;
    private Message message;

    private ViewHandlerImpl viewHandler;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(User user, ViewHandlerImpl viewHandler, Message message) {
        this.viewHandler = viewHandler;
        this.user = user;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            try (Socket socket = new Socket("localhost", 5000)) {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                connect();

                while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                    System.out.println("Listener: " + message.toString().replace("\n", ""));
                    user = message.getUser();
                    msgReceivedChat = message.getChatName();
                    msg = message.getMsg();

                    switch (message.getMessageType()) {
                        case CONNECTED -> main();
                        case JOINED_ROOM -> chat();
                        case RECEIVE -> receive();
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

    private void receive() {
        if(chatName.equals(msgReceivedChat)) {
            viewHandler.getChatController().getTaOutput().appendText(msg);
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
        outputStream.writeObject(new Message(user, MessageType.REGISTER));
    }

    @SneakyThrows
    private void login() {
        outputStream.writeObject(new Message(user, MessageType.LOGIN));
    }

    @SneakyThrows
    public void joinRoom(String chat) {
        chatName = chat;
        outputStream.writeObject(new Message(chat, MessageType.JOIN_ROOM));
    }

    @SneakyThrows
    public void createRoom(String chat) {
        chatName = chat;
        outputStream.writeObject(new Message(chat, MessageType.CREATE_ROOM));
    }

    @SneakyThrows
    public void sendMessage(String msg) {
        outputStream.writeObject(new Message(chatName, msg, MessageType.SEND));
    }

    @SneakyThrows
    public void auth() {
        viewHandler.launchLoginWindow();
    }

    @SneakyThrows
    public void main() {
        viewHandler.launchMainWindow();
    }

    @SneakyThrows
    public void chat() {
        viewHandler.launchChatWindow();
    }
}
