package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class Handler extends Thread {
    private final Socket socket;
    private final Validate validate = new Validate();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            System.out.println("A new client is connected: " + socket);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Message message;
            while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                System.out.println("Handler: " + message);
                switch (message.getMessageType()) {
                    case LOGIN -> {
                        User user;
                        if ((user = validate.login(message.getUser())) != null) {
                            outputStream.writeObject(new Message(user, MessageType.CONNECTED));
                        }
                    }
                    case REGISTER -> {
                        if (validate.register(message.getUser())) {
                            outputStream.writeObject(new Message(message.getUser(), MessageType.CONNECTED));
                        }
                    }
                    case JOIN_ROOM -> {
                        User user;
                        if ((user = validate.joinRoom(message.getUser(), message.getChat())) != null) {
                            outputStream.writeObject(new Message(user, message.getChat(), MessageType.JOINED_ROOM));
                        }
                    }
                    case CREATE_ROOM -> {
                        User user;
                        if ((user = validate.createRoom(message.getUser(), message.getChat())) != null) {
                            outputStream.writeObject(new Message(user, message.getChat(), MessageType.JOINED_ROOM));
                        }
                    }
                    case JOIN_PRIVATE -> {

                    }
                    case SEND -> {
                        Chat chat;
                        if ((chat = validate.sendToRoom(message.getUser(), message.getChat(), message.getMessage())) != null) {
                            outputStream.writeObject(new Message(message.getUser(), chat, message.getMessage(), MessageType.RECEIVE));
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }
}
