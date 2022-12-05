package com.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.PrintWriter;
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
    private String username;
    @EqualsAndHashCode.Include
    private String password;
    @EqualsAndHashCode.Include
    private List<String> ongoingMessages = new ArrayList<>();
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

    public void printPreviousMessages(PrintWriter write) {
        ongoingMessages.forEach(write::println);
    }
}
