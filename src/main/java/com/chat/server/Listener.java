package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import com.chat.view.ViewHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
public class Listener implements Runnable {
    private User user;
    private ViewHandler viewHandler;
    private Message message;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Listener(User user, ViewHandler viewHandler, Message message) {
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
                switch (message.getMessageType()) {
                    case LOGIN -> login();
                    case REGISTER -> register();
                }

                while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                    System.out.println("Listener: " + message);

                    switch (message.getMessageType()) {
                        case CONNECTED -> {
                            user = message.getUser();
                            viewHandler.launchMainWindow(this);
                        }
                        case JOINED_ROOM -> viewHandler.launchChatWindow(this, message.getChat());
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

    private void register() throws IOException {
        outputStream.writeObject(new Message(user, MessageType.REGISTER));
    }

    private void login() throws IOException {
        outputStream.writeObject(new Message(user, MessageType.LOGIN));
    }

    public void joinRoom(Chat chat) throws IOException {
        outputStream.writeObject(new Message(chat, MessageType.JOIN_ROOM));
    }

    public void createRoom(Chat chat) throws IOException {
        outputStream.writeObject(new Message(chat, MessageType.CREATE_ROOM));
    }

    public void sendMessage(Chat chat, String message) throws IOException {
        outputStream.writeObject(new Message(chat, message, MessageType.SEND));
    }
}
