package com.chat.app.view;

import com.chat.app.model.Chat;
import com.chat.app.model.Database;
import com.chat.app.model.User;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow(User user, Database database) throws IOException;

    void launchChatWindow(User user, Chat newChat, Database database) throws IOException;
}
