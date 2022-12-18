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

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    public Client(ViewHandler viewHandler, Message message) {
        this.viewHandler = viewHandler;
        this.user = message.getUser();
        this.message = message;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
            connect();


            while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                System.out.println("Client: " + message);
                user = message.getUser();
                chat = message.getChat();
                switch (message.getMessageType()) {
                    case CONNECTED -> viewHandler.launchMainWindow(this);
                    case JOINED_ROOM -> viewHandler.launchChatWindow(this);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void connect() {
        outputStream.writeObject(message);
        user = message.getUser();
        chat = message.getChat();
    }

    @SneakyThrows
    public void createRoom(Chat chat) {
        outputStream.writeObject(new Message(user, chat, MessageType.CREATE_ROOM));
    }

    @SneakyThrows
    public void joinRoom(Chat chat) {
        outputStream.writeObject(new Message(user, chat, MessageType.JOIN_ROOM));
    }

    @SneakyThrows
    public void auth() {
        viewHandler.launchLoginWindow();
    }
}
