package com.chat.server;

import com.chat.model.Database;
import com.chat.model.User;

/**
 * @author Tomas Kozakas
 */
public class DatabaseHolder {
    private final Database database = new Database();

    public DatabaseHolder() {
        database.importData();
    }

    public User login(User user) {
        if (!database.containsUser(user.getName())) {
            return null;
        }
        return !database.getUser(user.getName()).getPassword().equals(user.getName()) ? null : database.getUser(user.getName());
    }

    public User register(User user) {
        if (database.containsUser(user.getName())) {
            return null;
        }
        return user;
    }
}
