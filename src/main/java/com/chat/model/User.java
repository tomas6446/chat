package com.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements Serializable {
    private String name;
    private String password;
    private List<Chat> availableChat = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean containsChat(String chatName) {
        return availableChat.stream().anyMatch(chat -> chat.getName().equals(chatName));
    }

    public Chat getChat(String chatName) {
        return availableChat.stream().filter(chat -> chat.getName().equals(chatName)).findFirst().orElse(null);
    }

    public void sendMsg(String chatName, String msg) {
        availableChat.forEach(chat -> {
            if (chat != null && chat.getName().equals(chatName)) {
                chat.getMessages().add(msg);
            }
        });
    }
}
