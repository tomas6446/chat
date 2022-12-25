package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.scene.control.TextArea;
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
    private String msg;
    private ViewHandlerImpl viewHandler;
    private Message message;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private TextArea output;

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
                    chat = message.getChat();
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
        viewHandler.getChatController().getTaOutput().appendText(msg);
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
    public void joinRoom(Chat chat) {

    }

    @SneakyThrows
    public void createRoom(Chat chat) {

    }

    @SneakyThrows
    public void sendMessage(String message) {

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
