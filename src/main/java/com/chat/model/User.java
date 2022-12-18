package com.chat.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private String name;
    private String password;
    private List<String> availableChat = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public ObservableList<String> getAvailableChat() {
        return FXCollections.observableArrayList(availableChat);
    }

    public void addChat(String name) {
        availableChat.add(name);
    }
}
