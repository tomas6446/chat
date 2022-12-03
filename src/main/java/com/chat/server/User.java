package com.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class User {
    private BufferedReader input;
    private PrintWriter output;
    private String username;
    private boolean connectedToChatRoom;
    private String chatRoomName;
    public User(Socket socket) throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void println(String string) {
        output.println(string);
    }

    public String read() {
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnectedToChatRoom() {
        return connectedToChatRoom;
    }

    public void setConnectedToChatRoom(boolean connectedToChatRoom) {
        this.connectedToChatRoom = connectedToChatRoom;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}
