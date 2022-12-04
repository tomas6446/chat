package com.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;


/**
 * @author Tomas Kozakas
 */
public class Connection implements Runnable {
    private final Socket socket;
    private User user;
    private PrintWriter output;
    private BufferedReader input;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Menu().run();
        } catch (Exception e) {
            Server.shutDown();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public User getUser() {
        return user;
    }

    class Menu implements Runnable {
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
                throw new RuntimeException(e);
            }
        }

        private void quit() throws IOException {
            socket.close();
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
                throw new RuntimeException(e);
            }
        }

        private void logIn() {
            try {
                user = new User();
                output.println("Username: ");
                String username = input.readLine();
                output.println("Password: ");
                String password = input.readLine();
                Server.connectionList.forEach(connection -> {
                    if (Objects.equals(username, connection.user.getUsername())
                            && Objects.equals(password, connection.user.getPassword())) {
                        user = connection.getUser();
                    }
                });
                Server.broadCastServer(user.getUsername() + " connected!");
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                Server.broadCastServer("new user: " + user.getUsername() + " connected!");
                Server.importConnections();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void privateMessage() {
            try {
                String message;
                output.println("Recipient:");
                String recipient = input.readLine();

                while ((message = input.readLine()) != null) {
                    if (message.startsWith("/quit")) {
                        return;
                    } else if (message.startsWith("/return")) {
                        run();
                    } else {
                        Server.broadCastTo(recipient, user.getUsername() + ": " + message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void chatRoom() {
            try {
                output.println("1. Create chat room\n2. Connect to chat room\n0. Return\nChoice:");
                switch (Integer.parseInt(input.readLine())) {
                    case 1 -> {
                        output.println("Title: ");
                        user.setChatRoomName(input.readLine());
                        Server.chatRoomList.add(new ChatRoom(user.getChatRoomName()));
                        output.println("Connected to " + Server.chatRoomList.get(Server.chatRoomList.size() - 1).getName());
                    }
                    case 2 -> {
                        Server.chatRoomList.forEach(chatRoom -> output.println(Server.chatRoomList.indexOf(chatRoom) + 1 + ". " + chatRoom.getName()));
                        output.println("0. Return\nChoice: ");

                        // Connect to specific chatroom
                        Integer index = Integer.parseInt(input.readLine());
                        if (index - 1 > Server.chatRoomList.size() || index - 1 < 0) {
                            chatRoom();
                        }
                        user.setChatRoomName(Server.chatRoomList.get(index - 1).getName());
                        output.println("Connected to " + user.getChatRoomName());
                    }
                    default -> chatRoom();
                }

                user.setConnectedToChatRoom(true);
                Server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " joined the chat!");
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.startsWith("/quit")) {
                        Server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " left the chat");
                        return;
                    } else if (message.startsWith("/return")) {
                        Server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " left the chat");
                        user.setConnectedToChatRoom(false);
                        run();
                    } else {
                        Server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + ": " + message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}