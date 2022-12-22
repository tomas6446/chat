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
        return !database.getUser(user.getName()).getPassword().equals(user.getName()) ? null : database.getUser(user.getName());
    }

    public User joinRoom(User user, Chat chat) {
        if (!database.containsChat(chat.getName())) {
            return null;
        }
        user.setConnectedChat(chat.getName());
        if (!user.containsChat(chat.getName())) {
            user.addChat(database.getChat(chat.getName()));
            database.replaceUser(user);
            database.exportData();
        }
        return user;
    }

    public boolean register(User user) {
        if (database.containsChat(user.getName())) {
            return false;
        }
        database.addUser(user);
        database.exportData();
        return true;
    }

    public User createRoom(User user, Chat chat) {
        if (database.containsChat(chat.getName())) {
            return null;
        }
        user.addChat(chat);
        user.setConnectedChat(chat.getName());
        database.replaceUser(user);
        database.addChat(chat);
        database.exportData();

        return user;
    }

    public Chat sendToRoom(Chat chat, String message) {
        if (!database.containsChat(chat.getName())) {
            return null;
        }
        chat.addMessage(message);
        // Each user that has this chat in the list gets a message
        database.getUserMap().forEach((s, user) -> user.getAvailableChat().forEach(c -> {
            if (Objects.equals(c.getName(), chat.getName())) {
                c.getMessages().add(message);
            }
        }));
        database.exportData();
        return chat;
    }
}
