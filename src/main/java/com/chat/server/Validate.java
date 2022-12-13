package com.chat.server;

import com.chat.model.Database;
import com.chat.model.User;

/**
 * @author Tomas Kozakas
 */
public class Validate {
    private final Database database = new Database();

    public Validate() {
        database.importData();
    }

    public boolean connect(User user) {
        if (!database.containsUser(user.getName())) {
            return false;
        }
        User foundUser = database.getUser(user.getName());
        return foundUser.getPassword().equals(user.getName());
    }
}
