package com.chat.server;

import com.chat.controller.impl.ChatController;
import com.chat.controller.impl.MainController;
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
    private Chat chat;
    private ViewHandler viewHandler;
    private Message message;

    private ChatController chatController;
    private MainController mainController;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket socket;

    public Listener(User user, ViewHandler viewHandler, Message message) {
        this.viewHandler = viewHandler;
        this.user = user;
        this.message = message;
        this.chatController = new ChatController(viewHandler, this);
        this.mainController = new MainController(viewHandler, this);
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
            switch (message.getMessageType()) {
                case LOGIN -> login();
                case REGISTER -> register();
            }
            while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                System.out.println("Listener: " + message);
                user = message.getUser();
                chat = message.getChat();
                String receivedMsg = message.getMessage();

                switch (message.getMessageType()) {
                    case CONNECTED -> viewHandler.launchMainWindow(this);
                    case JOINED_ROOM -> viewHandler.launchChatWindow(this);
                    case RECEIVE -> chatController.receive(receivedMsg);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void register() throws IOException {
        outputStream.writeObject(new Message(user, MessageType.REGISTER));
    }

    public void login() throws IOException {
        outputStream.writeObject(new Message(user, MessageType.LOGIN));
    }

    public void joinRoom(Chat chat) throws IOException {
        outputStream.writeObject(new Message(user, chat, MessageType.JOIN_ROOM));
    }

    public void createRoom(Chat chat) throws IOException {
        outputStream.writeObject(new Message(user, chat, MessageType.CREATE_ROOM));
    }

    public void sendMessage(Chat chat, String message) throws IOException {
        outputStream.writeObject(new Message(user, chat, message, MessageType.SEND));
    }

    public void joinPrivate(String recipient) throws IOException {
        outputStream.writeObject(new Message(user, recipient, MessageType.JOIN_PRIVATE));
    }
}
