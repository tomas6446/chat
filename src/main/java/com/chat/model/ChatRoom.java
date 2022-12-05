package com.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.PrintWriter;
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
    private List<String> messageList;

    public void print(String message) {
        messageList.add(message);
    }

    public void printLastMessages(PrintWriter write) {
        messageList.forEach(write::println);
    }
}
