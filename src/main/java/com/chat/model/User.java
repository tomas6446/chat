package com.chat.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public ObservableList<Chat> getAvailableChat() {
        return FXCollections.observableArrayList(availableChat);
    }
}
