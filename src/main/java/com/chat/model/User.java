package com.chat.model;

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
    private boolean connectedToChatRoom;
    private String chatRoomName;
}
