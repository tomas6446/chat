package com.chat.server;

import java.net.Socket;


/**
 * @author Tomas Kozakas
 */
public class ConnectionHandler implements Runnable {
    private User user;
    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            user = new User(socket);
            user.println("Please enter an username: ");
            user.setUsername(user.read());
            Server.broadCastServer(user.getUsername() + " connected!");

            new Menu().run();
        } catch (Exception e) {
            Server.shutDown();
        }
    }

    public void sendMessage(String message) {
        user.println(message);
    }

    public User getUser() {
        return user;
    }

    class Menu implements Runnable {
        @Override
        public void run() {
            user.println("1. Private message\n2. Chat room\nChoice:");
            switch (Integer.parseInt(user.read())) {
                case 1 -> privateMessage();
                case 2 -> chatRoom();
                default -> run();
            }
        }

        private void privateMessage() {
            String message;
            user.println("Recipient:");
            String recipient = user.read();

            while ((message = user.read()) != null) {
                if (message.startsWith("/quit")) {
                    return;
                } else if (message.startsWith("/return")) {
                    run();
                } else {
                    Server.broadCastTo(recipient, user.getUsername() + ": " + message);
                }
            }
        }

        private void chatRoom() {
            user.println("1. Create chat room\n2. Connect to chat room\nChoice:");
            switch (Integer.parseInt(user.read())) {
                case 1 -> {
                    user.println("Title: ");
                    Server.chatRoomList.add(new ChatRoom(user.read()));
                    user.println("Connected to " + Server.chatRoomList.get(Server.chatRoomList.size() - 1).getName());
                }
                case 2 -> {
                    Server.chatRoomList.forEach(chatRoom -> user.println(Server.chatRoomList.indexOf(chatRoom) + 1 + ". " + chatRoom.getName()));
                    user.println("Choice: ");
                    // Connect to specific chatroom
                    Integer index = Integer.parseInt(user.read());
                    if (index - 1 > Server.chatRoomList.size() || index - 1 < 0) {
                        chatRoom();
                    }
                    user.setChatRoomName(Server.chatRoomList.get(index - 1).getName());
                    user.println("Connected to " + user.getChatRoomName());
                }
                default -> chatRoom();
            }

            user.setConnectedToChatRoom(true);
            Server.broadCastChatRoom(user.getChatRoomName(), user.getUsername() + " joined the chat!");
            String message;
            while ((message = user.read()) != null) {
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
        }
    }
}