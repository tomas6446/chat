package com.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @JsonIgnore
    private boolean connectedToChatRoom;
    @JsonIgnore
    private String chatRoomName;
}
