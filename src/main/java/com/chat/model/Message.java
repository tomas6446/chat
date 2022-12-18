package com.chat.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Message implements Serializable {
    private User user;
    private Chat chat;
    private MessageType messageType;

    public Message(User user, Chat chat, MessageType messageType) {
        this.user = user;
        this.chat = chat;
        this.messageType = messageType;
    }

    public Message(User user, MessageType messageType) {
        this.user = user;
        this.messageType = messageType;
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }
}
