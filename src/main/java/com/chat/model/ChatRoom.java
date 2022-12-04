package com.chat.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@RequiredArgsConstructor
public class ChatRoom {
    private final String name;
    private List<String> messageList;
}
