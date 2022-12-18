package com.chat.model;

import com.chat.server.Client;
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
    private Client client;
    private String message;
    private User user;
    private Chat chat;
    private MessageType messageType;

    public Message(Client client, MessageType messageType) {
        this.client = client;
        this.messageType = messageType;
    }

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
