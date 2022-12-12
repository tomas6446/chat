package com.chat.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String password;
    @EqualsAndHashCode.Include
    private List<Chat> chatList = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public ObservableList<Chat> getChatList() {
        return FXCollections.observableArrayList(chatList);
    }

    public void addChat(Chat newChat) {
        chatList.add(newChat);
    }
}
