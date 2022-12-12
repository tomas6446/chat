package com.chat.server.server;

import com.chat.app.view.impl.ViewHandlerImpl;
import com.chat.server.model.ChatRoom;
import com.chat.server.model.User;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tomas Kozakas
 */
@Getter
class Menu extends Application implements Runnable {
    private final Server server;
    private final PrintWriter output;
    private final BufferedReader input;
    private User user = new User();

    public Menu(Server server, BufferedReader bufferedReader, PrintWriter printWriter) {
        this.server = server;
        this.input = bufferedReader;
        this.output = printWriter;
    }

    @Override
    public void run() {
        try {
            output.println("1. Log in\n2. Sign up\n0. Quit\nChoice:");
            switch (Integer.parseInt(input.readLine())) {
                case 1 -> logIn();
                case 2 -> signUp();
                case 0 -> quit();
                default -> run();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void quit() throws IOException {
        input.close();
        output.close();
        server.shutDown();
    }

    private void menu() throws IOException {
        output.println("1. Private message\n2. Chat room\n0. Return\nChoice:");
        switch (Integer.parseInt(input.readLine())) {
            case 1 -> privateMessage();
            case 2 -> chatRoom();
            case 0 -> run();
            default -> menu();
        }
    }

    private void logIn() throws IOException {
        output.println("Username: ");
        String username = input.readLine();
        output.println("Password: ");
        String password = input.readLine();

        if (server.getUsers().containsKey(username)) {
            User foundUser = server.getUsers().get(username);
            if (Objects.equals(foundUser.getPassword(), password)) {
                this.user = foundUser;
            }
        } else {
            output.println("User not resolved");
            run();
        }
        output.println("Successfully connected to server");
        server.broadCastServer(user.getUsername() + " connected!");
    }

    private void signUp() throws IOException {
        output.println("Please enter an username: ");
        String username = input.readLine();
        output.println("Please enter a password: ");
        String password = input.readLine();

        if (server.getUsers().containsKey(username)) {
            output.println("User already exists");
            run();
        }
        user = new User(username, password);
        server.getUsers().put(username, user);
        server.broadCastServer("new user: " + user.getUsername() + " connected!");
        server.exportData();
    }

    private void privateMessage() throws IOException {
        String recipientName = null;
        output.println("1. Ongoing messages\n2. Send new message\n0. Return\nChoice:");
        switch (Integer.parseInt(input.readLine())) {
            case 1 -> {
                if (user.getOngoingMessages().isEmpty()) {
                    output.println("The list is empty");
                    privateMessage();
                }

                output.println("Ongoing conversations");
                user.printOngoingConversationNames(output);
                output.println("Recipient:");
                recipientName = input.readLine();
                if (user.getOngoingMessages().containsKey(recipientName)) {
                    user.printPrevPrivateMessages(output, recipientName);
                } else {
                    output.println("No user with this name");
                    privateMessage();
                }
            }
            case 2 -> {
                output.println("Recipient username:");
                recipientName = input.readLine();
                if (server.getUsers().containsKey(recipientName)) {
                    user.getOngoingMessages().put(recipientName, new ArrayList<>());
                } else {
                    output.println("No user with this name");
                    privateMessage();
                }
            }
            case 0 -> menu();
            default -> privateMessage();
        }
        output.println("Chatting with " + recipientName);
        user.setConnectedToPrivateMessages(true);
        String message;
        while ((message = input.readLine()) != null) {
            if (message.startsWith("/quit")) {
                user.setConnectedToPrivateMessages(false);
                quit();
            } else if (message.startsWith("/return")) {
                user.setConnectedToPrivateMessages(false);
                menu();
            } else {
                server.broadCastTo(user.getUsername(), recipientName, user.getUsername() + ": " + message);
            }
        }
    }

    private void chatRoom() throws IOException {
        String title = null;
        output.println("1. Connect to chat room\n2. Create chat room\n0. Return\nChoice:");
        switch (Integer.parseInt(input.readLine())) {
            case 1 -> {
                if (server.getChatRooms().isEmpty()) {
                    output.println("The list is empty");
                    chatRoom();
                }
                output.println("Available chat rooms");
                Set<String> chatNames = server.getChatRooms().keySet();
                for (String chatName : chatNames) {
                    output.println(chatName);
                }
                output.println("Connect to: ");
                title = input.readLine();

                if (server.getChatRooms().containsKey(title)) {
                    user.setConnectedToChatRoom(true);
                    user.setChatRoomName(title);
                    server.getChatRooms().get(title).printPrevMessages(output);
                } else {
                    output.println("No chat rooms with this name");
                    chatRoom();
                }
            }
            case 2 -> {
                output.println("Chat title: ");
                title = input.readLine();
                if (!title.isEmpty() && !server.getChatRooms().containsKey(title)) {
                    user.setConnectedToChatRoom(true);
                    user.setChatRoomName(title);
                    server.getChatRooms().put(title, new ChatRoom(title, new ArrayList<>()));
                    server.exportData();
                } else {
                    output.println("Invalid name for chat room");
                    chatRoom();
                }
            }
            case 0 -> menu();
            default -> chatRoom();
        }

        output.println("Connected to " + title);
        server.broadCastChatRoom(title, user.getUsername() + " joined the chat!");
        String message;
        while ((message = input.readLine()) != null) {
            if (message.startsWith("/quit")) {
                user.setConnectedToChatRoom(false);
                server.broadCastChatRoom(title, user.getUsername() + " left the chat");
                quit();
            } else if (message.startsWith("/return")) {
                user.setConnectedToChatRoom(false);
                server.broadCastChatRoom(title, user.getUsername() + " left the chat");
                menu();
            } else {
                server.broadCastChatRoom(title, user.getUsername() + ": " + message);
                server.getChatRooms().get(title).addMessage(user.getUsername() + ": " + message);
                server.exportData();
            }
        }
    }

    public void println(String message) {
        output.println(message);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new ViewHandlerImpl(stage).launchLoginWindow();
    }
}