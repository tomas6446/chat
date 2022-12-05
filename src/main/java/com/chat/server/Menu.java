package com.chat.server;

import com.chat.model.ChatRoom;
import com.chat.model.User;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

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
    }

    private void menu() {
        try {
            output.println("1. Private message\n2. Chat room\n0. Return\nChoice:");
            switch (Integer.parseInt(input.readLine())) {
                case 1 -> privateMessage();
                case 2 -> chatRoom();
                default -> run();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void logIn() {
        try {
            output.println("Username: ");
            String username = input.readLine();
            output.println("Password: ");
            String password = input.readLine();
            server.getUsersList().forEach(user -> {
                if (Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())) {
                    this.user = user;
                }
            });
            if (this.user == null) {
                output.println("User not resolved");
                run();
            }
            server.broadCastServer(user.getUsername() + " connected!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void signUp() {
        try {
            // TODO: handle username and password creation
            user = new User();
            output.println("Please enter an username: ");
            user.setUsername(input.readLine());
            output.println("Please enter a password: ");
            user.setPassword(input.readLine());
            server.getUsersList().add(user);
            server.broadCastServer("new user: " + user.getUsername() + " connected!");
            server.exportConnections();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void privateMessage() {
        try {
            output.println("Recipient:");
            String recipient = input.readLine();

            String message;
            while ((message = input.readLine()) != null) {
                if (message.startsWith("/quit")) {
                    return;
                } else if (message.startsWith("/return")) {
                    run();
                } else {
                    server.broadCastTo(recipient, user.getUsername() + ": " + message);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void chatRoom() {
        try {
            output.println("1. Create chat room\n2. Connect to chat room\n0. Return\nChoice:");
            switch (Integer.parseInt(input.readLine())) {
                case 1 -> {
                    output.println("Title: ");
                    user.setChatRoomName(input.readLine());
                    server.getChatRoomList().add(new ChatRoom(user.getChatRoomName(), new ArrayList<>()));
                    output.println("Connected to " + server.getChatRoomList().get(server.getChatRoomList().size() - 1).getName());
                    server.exportChatRoomList();
                }
                case 2 -> {
                    server.getChatRoomList().forEach(chatRoom -> output.println(server.getChatRoomList().indexOf(chatRoom) + 1 + ". " + chatRoom.getName()));
                    output.println("0. Return\nChoice: ");

                    // Connect to specific chatroom
                    int index = Integer.parseInt(input.readLine()) - 1;
                    if (index > server.getChatRoomList().size() || index < 0) {
                        menu();
                    }
                    user.setChatRoomName(server.getChatRoomList().get(index).getName());
                    output.println("Connected to " + user.getChatRoomName());
                    server.getChatRoomList().get(index).printLastMessages(output);
                }
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void println(String message) {
        output.println(message);
    }
}