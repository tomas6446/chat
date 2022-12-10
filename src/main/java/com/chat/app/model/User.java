package com.chat.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private String name;
    @EqualsAndHashCode.Include
    private String password;
    @EqualsAndHashCode.Include
    private Map<String, PrivateMessage> ongoingMessages = new HashMap<>();
    @EqualsAndHashCode.Include
    private List<String> chatRoomNames = new ArrayList<>();
    @JsonIgnore
    private boolean connectedToChatRoom;
    @JsonIgnore
    private boolean connectedToPrivateMessages;
    @JsonIgnore
    private String chatRoomName;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
