package com.chat.server;

import com.chat.model.Chat;
import com.chat.model.Database;
import com.chat.model.User;

import java.util.Objects;

/**
 * @author Tomas Kozakas
 */
public class Validate {
    private final Database database = new Database();

    public Validate() {
        database.importData();
    }

    public User login(User user) {
        if (!database.containsUser(user.getName())) {
            return null;
        }
        if (!database.getUser(user.getName()).getPassword().equals(user.getName())) {
            return null;
        }
        return database.getUser(user.getName());
    }

    public User joinRoom(User user, Chat chat) {
        if (!database.containsChat(chat.getName())) {
            return null;
        }
        if (!user.getChatList().contains(chat)) {
            user.addChat(chat);
            database.replaceUser(user);
            database.exportData();
        }

        return user;
    }

    public boolean register(User user) {
        if (database.containsUser(user.getName())) {
            return false;
        }
        database.addUser(user);
        database.exportData();
        return true;
    }

    public User createRoom(User user, Chat chat) {
        if (!database.containsChat(chat.getName())) {
            return null;
        }
        database.addChat(chat);
        user.addChat(chat);
        database.replaceUser(user);

        database.exportData();
        return user;
    }

    public Chat sendToRoom(User user, Chat chat, String message) {
        if (!database.containsChat(chat.getName())) {
            return null;
        }

        chat.getMessages().add(message);
        // Add for user that sends the message
        user.getChatList().forEach(c -> {
            if (Objects.equals(c.getName(), chat.getName())) {
                c.getMessages().add(message);
            }
        });
        // Each user that has this chat in the list gets a message
        database.getUserMap().forEach((s, u) -> u.getChatList().forEach(c -> {
            if (Objects.equals(c.getName(), chat.getName())) {
                c.getMessages().add(message);
            }
        }));
        database.exportData();
        return chat;
    }
}
