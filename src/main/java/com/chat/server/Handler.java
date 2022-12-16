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
public class Handler implements Runnable {
    private final Validate validate = new Validate();

    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Handler(Server server, Socket socket) {
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
                switch (message.getMessageType()) {
                    case LOGIN -> {
                        if (validate.login(user) != null) {
                            outputStream.writeObject(new Message(user, chat, MessageType.CONNECTED));
                        }
                    }
                    case REGISTER -> {
                        if (validate.register(user)) {
                            outputStream.writeObject(new Message(user, chat, MessageType.CONNECTED));
                        }
                    }
                    case CREATE_ROOM -> {
                        if (validate.createRoom(user, chat)) {
                            outputStream.writeObject(new Message(user, chat, MessageType.JOINED_ROOM));
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
