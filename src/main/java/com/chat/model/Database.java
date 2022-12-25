package com.chat.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
public class Database {
    private List<String> chatList = new ArrayList<>();
    private Map<String, User> userMap = new HashMap<>();

    public Database() {
        importData();
    }

    public void importData() {
        try {
            List<User> userList = new ObjectMapper().readValue(new File("data/user.json"), new TypeReference<>() {
            });
            userMap = userList.stream().collect(Collectors.toMap(User::getName, Function.identity()));

            chatList = new ObjectMapper().readValue(new File("data/chat.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Unable to import data");
            e.printStackTrace();
        }
    }

    public void exportData() {
        try {
            List<User> userList = userMap.values().stream().toList();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new FileWriter("data/user.json"), userList);
            objectMapper.writeValue(new FileWriter("data/chat.json"), chatList);
        } catch (IOException e) {
            System.err.println("Unable to export data");
            e.printStackTrace();
        }
    }

    public boolean containsUser(String username) {
        return userMap.containsKey(username);
    }

    public User getUser(String username) {
        return userMap.get(username);
    }

    public Chat getChat(String chatName) {
        return userMap.values()
                .stream()
                .flatMap(user -> user.getAvailableChat().stream())
                .filter(chat -> chat.getName().equals(chatName))
                .findFirst().orElse(null);
    }

    public void replaceUser(User user) {
        userMap.replace(user.getName(), user);
    }
}
