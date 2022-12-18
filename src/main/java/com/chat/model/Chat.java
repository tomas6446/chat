package com.chat.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Chat implements Serializable {
    private List<String> messages = new ArrayList<>();
    private String name;

    public Chat(String name) {
        this.name = name;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
