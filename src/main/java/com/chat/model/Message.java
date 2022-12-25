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
    private String room;
    private User user;

    public Message(User user, MessageType messageType) {
        this.user = user;
        this.messageType = messageType;
    }
}
