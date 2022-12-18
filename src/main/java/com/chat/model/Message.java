package com.chat.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private String message;
    private User user;
    private Chat chat;
    private MessageType messageType;

    public Message(User user, MessageType messageType) {
        this.user = user;
        this.messageType = messageType;
    }

    public Message(User user, Chat chat, MessageType messageType) {
        this.user = user;
        this.chat = chat;
        this.messageType = messageType;
    }

    public Message(User user, Chat chat, String message, MessageType messageType) {
        this.user = user;
        this.chat = chat;
        this.message = message;
        this.messageType = messageType;
    }
}
