package com.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private MessageType messageType;
    private String msg;
    private String chatName;
    private User user;

    public Message(User user, MessageType messageType) {
        this.user = user;
        this.messageType = messageType;
    }

    public Message(String chatName, MessageType messageType) {
        this.chatName = chatName;
        this.messageType = messageType;
    }

    public Message(String chatName, String msg, MessageType messageType) {
        this.chatName = chatName;
        this.msg = msg;
        this.messageType = messageType;
    }

    public Message(User user, String chatName, MessageType messageType) {
        this.user = user;
        this.chatName = chatName;
        this.messageType = messageType;
    }

    public Message(User user, String chatName, String msg, MessageType messageType) {
        this.user = user;
        this.chatName = chatName;
        this.msg = msg;
        this.messageType = messageType;
    }
}
