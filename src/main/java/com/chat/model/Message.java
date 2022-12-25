package com.chat.model;

import com.chat.server.Client;
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
    private ViewHandlerImpl viewHandler;
    private String message;
    private User user;
    private Chat chat;
    private TextArea output;
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

    public Message(User user, Chat chat, String message, TextArea output, MessageType messageType) {
        this.user = user;
        this.chat = chat;
        this.message = message;
        this.output = output;
        this.messageType = messageType;
    }

    public Message(User user, ViewHandlerImpl viewHandler, MessageType messageType) {
        this.user = user;
        this.viewHandler = viewHandler;
        this.messageType = messageType;
    }
}
