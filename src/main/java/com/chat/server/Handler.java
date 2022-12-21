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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class Handler extends Thread {
    private List<Client> connections;
    private final Socket socket;
    private final Validate validate = new Validate();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private List<Client> clients = new ArrayList<>();

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
                User user = message.getUser();
                Chat chat = message.getChat();
                String msg = message.getMessage();

                switch (message.getMessageType()) {
                    case LOGIN -> {
                        if ((user = validate.login(user)) != null) {
                            outputStream.writeObject(new Message(user, chat, MessageType.CONNECTED));
                        }
                    }
                    case JOIN_ROOM -> {
                        if ((user = validate.joinRoom(user, chat)) != null) {
                            outputStream.writeObject(new Message(user, chat, MessageType.JOINED_ROOM));
                        }
                    }
                    case CREATE_ROOM -> {
                        if ((user = validate.createRoom(user, chat)) != null) {
                            outputStream.writeObject(new Message(user, chat, MessageType.JOINED_ROOM));
                        }
                    }
                    case REGISTER -> {
                        if (validate.register(user)) {
                            outputStream.writeObject(new Message(user, chat, MessageType.CONNECTED));
                        }
                    }
                    case SEND -> {
                        if ((chat = validate.sendToRoom(chat, msg)) != null) {
                            outputStream.writeObject(new Message(user, chat, msg, MessageType.RECEIVE));
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
