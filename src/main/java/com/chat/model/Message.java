package com.chat.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private String message;
    private User user;
    private MessageType messageType;

    public Message(User user, MessageType messageType) {
        this.user = user;
        this.messageType = messageType;
    }
}
