package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
@Getter
public class ServerThread extends Thread {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private final Socket socket;
    private final ServerHandler serverHandler;
    private final DatabaseHolder databaseHolder = new DatabaseHolder();

    private User loggedInUser;

    public ServerThread(Socket socket, ServerHandler serverHandler) {
        this.socket = socket;
        this.serverHandler = serverHandler;
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
                System.out.println("Handler: " + message.toString().replace("\n", ""));

                switch (message.getMessageType()) {
                    case LOGIN -> {
                        loggedInUser = databaseHolder.login(message.getUser());
                        writeMessage(loggedInUser != null, outputStream, new Message(loggedInUser, MessageType.CONNECTED));
                    }
                    case REGISTER -> {
                        loggedInUser = databaseHolder.register(message.getUser());
                        writeMessage(loggedInUser != null, outputStream, new Message(loggedInUser, MessageType.CONNECTED));
                    }
                    case JOIN_ROOM -> joinRoom(outputStream, message);
                    case CREATE_ROOM -> createRoom(outputStream, message);
                    case SEND -> sendAll(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    private static void writeMessage(boolean send, ObjectOutputStream outputStream, Message message) throws IOException {
        if (send) {
            outputStream.writeObject(message);
        }
    }

    private void joinRoom(ObjectOutputStream outputStream, Message message) throws IOException {

    }

    private void createRoom(ObjectOutputStream outputStream, Message message) throws IOException {

    }

    private void sendAll(Message message) throws IOException {

    }
}
