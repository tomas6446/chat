package com.chat.model;

import com.chat.view.impl.ViewHandlerImpl;
import javafx.scene.control.TextArea;
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
    private String msg;
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

    public Message(User user, Chat chat, String msg, MessageType messageType) {
        this.user = user;
        this.chat = chat;
        this.msg = msg;
        this.messageType = messageType;
    }
}
