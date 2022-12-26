package com.chat.server;

import com.chat.model.*;
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
    private final Database database = new Database();

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
                    case LOGIN -> login(message);
                    case REGISTER -> register(message);
                    case JOIN_ROOM -> joinRoom(message);
                    case CREATE_ROOM -> createRoom(message);
                    case SEND -> send(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }


    private void send(Message message) throws IOException {
        String chatName = message.getChatName();
        String msg = message.getMsg();

        for (ServerThread serverThread : serverHandler.getServerThreads()) {
            ObjectOutputStream out = serverThread.getOutputStream();
            User user = serverThread.getLoggedInUser();
            if (user != null) {
                user.sendMsg(chatName, msg);
                database.replaceUser(user);
                database.exportData();
                out.writeObject(new Message(user, chatName, msg, MessageType.RECEIVE));
            }
        }
    }

    private void createRoom(Message message) throws IOException {
        String chatName = message.getChatName();
        if (database.getChat(chatName) != null) {
            return;
        }
        database.getChatList().add(chatName);
        loggedInUser.getAvailableChat().add(new Chat(chatName));
        database.replaceUser(loggedInUser);
        database.exportData();
        outputStream.writeObject(new Message(loggedInUser, chatName, MessageType.JOINED_ROOM));
    }

    private void joinRoom(Message message) throws IOException {
        String chatName = message.getChatName();
        Chat chat;

        if ((chat = database.getChat(chatName)) == null) {
            return;
        }
        if (!loggedInUser.containsChat(chatName)) {
            loggedInUser.getAvailableChat().add(chat);
            database.replaceUser(loggedInUser);
            database.exportData();
        }
        outputStream.writeObject(new Message(loggedInUser, chatName, MessageType.JOINED_ROOM));
    }

    private void login(Message message) throws IOException {
        User user = message.getUser();
        if (!database.containsUser(user.getName())) {
            return;
        }
        User foundUser = database.getUser(user.getName());
        if (user.getPassword().equals(foundUser.getPassword())) {
            loggedInUser = foundUser;
            outputStream.writeObject(new Message(loggedInUser, MessageType.CONNECTED));
        }
    }

    private void register(Message message) throws IOException {
        User user = message.getUser();
        if (database.containsUser(user.getName())) {
            return;
        }
        loggedInUser = user;
        database.getUserMap().put(user.getName(), user);
        database.exportData();
        outputStream.writeObject(new Message(loggedInUser, MessageType.CONNECTED));
    }
}
