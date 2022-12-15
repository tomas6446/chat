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

    public boolean joinRoom(Chat chat) {
        return database.containsChat(chat.getName());
    }

    public boolean register(User user) {
        if (database.containsChat(user.getName())) {
            return false;
        }
        database.addUser(user);
        database.exportData();
        return true;
    }

    public boolean createRoom(Chat chat) {
        if (database.containsChat(chat.getName())) {
            return false;
        }
        database.addChat(chat);
        database.exportData();
        return true;
    }

    public boolean sendToRoom(Chat chat, String message) {
        if (!database.containsChat(chat.getName())) {
            return false;
        }

        // Each user that has this chat in the list gets a message
        database.getUserMap().forEach((s, user) -> user.getChatList().forEach(c -> {
            if (Objects.equals(c.getName(), chat.getName())) {
                c.getMessages().add(message);
            }
        }));
        database.exportData();
        return true;
    }
}
