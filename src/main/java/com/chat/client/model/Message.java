package com.chat.client.model;

/**
 * @author Tomas Kozakas
 */
public class Message {
    String sender;
    String msg;
    String recipient;

    public Message(String sender, String msg, String recipient) {
        this.sender = sender;
        this.msg = msg;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
