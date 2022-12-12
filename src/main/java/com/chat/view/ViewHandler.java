package com.chat.view;

import com.chat.model.Chat;
import com.chat.model.Database;
import com.chat.model.User;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow(User user, Database database) throws IOException;

    void launchChatWindow(User user, Chat newChat, Database database) throws IOException;
}
