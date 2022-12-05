package com.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    private HashMap<String, List<String>> ongoingConversations = new HashMap<>();
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

    public void printOngoingConversations(PrintWriter write) {
        Set<String> conversationName = ongoingConversations.keySet();
        conversationName.forEach(username -> {
            int i = 0;
            write.println(++i + ". " + username);
        });
    }

    public void printPreviousMessages(PrintWriter write, String recipient) {
        write.println(ongoingConversations.get(recipient));
    }

    public void addMessage(String recipient, String message) {
        ongoingConversations.get(recipient).add(message);
    }
}
