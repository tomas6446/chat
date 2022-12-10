package com.chat.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
@AllArgsConstructor
public class ChatRoom extends Chat {
    private List<String> messages = new ArrayList<>();

    public ChatRoom() {
        super();
    }
}
