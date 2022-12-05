package com.chat.server;

import com.chat.model.ChatRoom;
import com.chat.model.User;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Tomas Kozakas
 */
@Getter
class Menu implements Runnable {

    private final Server server;
    private final PrintWriter output;
    private final BufferedReader input;
    private User user;

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
            menu();
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
        server.getUserList().forEach(user -> {
            if (Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())) {
                this.user = user;
            }
        });
        if (this.user == null) {
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
        server.getUserList().forEach(user -> {
            if (Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())) {
                output.println("User already exists");
                run();
            }
        });
        user = new User(username, password);
        server.getUserList().add(user);
        server.broadCastServer("new user: " + user.getUsername() + " connected!");
        server.exportUser();
    }

    private void privateMessage() throws IOException {
        String recipientName = null;
        output.println("1. Ongoing conversations\n2. Start new one\n0. Return\nChoice:");
        switch (Integer.parseInt(input.readLine())) {
            case 1 -> {
                if (user.getOngoingConversations().isEmpty()) {
                    output.println("The list is empty");
                    privateMessage();
                }

                user.printOngoingConversations(output);
                output.println("Recipient:");
                recipientName = input.readLine();

                user.printPreviousMessages(output, recipientName);
            }
            case 2 -> {
                output.println("Recipient username:");
                recipientName = input.readLine();
                user.getOngoingConversations().put(recipientName, new ArrayList<>());
            }
            case 0 -> menu();
            default -> privateMessage();
        }
        output.println("The recipient is " + recipientName);
        user.setConnectedToPrivateMessages(true);
        String message;
        while ((message = input.readLine()) != null) {
            if (message.startsWith("/quit")) {
                return;
            } else if (message.startsWith("/return")) {
                run();
            } else {
                server.broadCastTo(recipientName, user.getUsername() + ": " + message);
            }
        }
    }

    private void chatRoom() throws IOException {
        output.println("1. Create chat room\n2. Connect to chat room\n0. Return\nChoice:");
        switch (Integer.parseInt(input.readLine())) {
            case 1 -> {
                output.println("Title: ");
                user.setChatRoomName(input.readLine());
                server.getChatRoomList().add(new ChatRoom(user.getChatRoomName(), new ArrayList<>()));
                output.println("Connected to " + server.getChatRoomList().get(server.getChatRoomList().size() - 1).getName());
                server.exportChatRoom();
            }
            case 2 -> {
                server.getChatRoomList().forEach(chatRoom -> output.println(server.getChatRoomList().indexOf(chatRoom) + 1 + ". " + chatRoom.getName()));
                output.println("0. Return\nChoice: ");

                // Connect to specific chatroom
                int index = Integer.parseInt(input.readLine()) - 1;
                if (index > server.getChatRoomList().size() || index < 0) {
                    chatRoom();
                }
                user.setChatRoomName(server.getChatRoomList().get(index).getName());
                output.println("Connected to " + user.getChatRoomName());
                server.getChatRoomList().get(index).printPreviousMessages(output);
            }
            case 0 -> menu();
            default -> chatRoom();
        }

        user.setConnectedToChatRoom(true);
        server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " joined the chat!");
        String message;
        while ((message = input.readLine()) != null) {
            if (message.startsWith("/quit")) {
                user.setConnectedToChatRoom(false);
                server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " left the chat");
                quit();
            } else if (message.startsWith("/return")) {
                user.setConnectedToChatRoom(false);
                server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " left the chat");
                menu();
            } else {
                server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + ": " + message);
            }
        }
        server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " left the chat");
    }

    public void println(String message) {
        output.println(message);
    }
}