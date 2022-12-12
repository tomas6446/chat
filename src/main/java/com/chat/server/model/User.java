package com.chat.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String username;
    @EqualsAndHashCode.Include
    private String password;
    @EqualsAndHashCode.Include
    private Map<String, List<String>> ongoingMessages = new HashMap<>();
    @JsonIgnore
    private boolean connectedToChatRoom;
    @JsonIgnore
    private boolean connectedToPrivateMessages;
    @JsonIgnore
    private String chatRoomName;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void printOngoingConversationNames(PrintWriter write) {
        List<String> conversationName = ongoingMessages.keySet().stream().toList();
        conversationName.forEach(write::println);
    }

    public void printPrevPrivateMessages(PrintWriter write, String recipient) {
        List<String> messages = ongoingMessages.get(recipient);
        messages.forEach(write::println);
    }

    public void addMessage(String recipient, String message) {
        if (!ongoingMessages.containsKey(recipient)) {
            ongoingMessages.put(recipient, new ArrayList<>());
        }
        ongoingMessages.get(recipient).add(message);
    }
}
