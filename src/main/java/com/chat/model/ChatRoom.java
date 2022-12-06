package com.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String name;
    private List<String> messages = new ArrayList<>();

    public void printPrevMessages(PrintWriter write) {
        messages.forEach(write::println);
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
