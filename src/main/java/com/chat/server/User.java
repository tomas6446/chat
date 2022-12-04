package com.chat.server;

/**
 * @author Tomas Kozakas
 */
public class User {
    private String username;
    private String password;
    private boolean connectedToChatRoom;
    private String chatRoomName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
