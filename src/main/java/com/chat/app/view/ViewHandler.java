package com.chat.app.view;

import com.chat.app.model.Chat;
import com.chat.app.model.User;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow(User user) throws IOException;

    void launchRegisterWindow(Map<String, User> users) throws IOException;

    void launchChatWindow(User user, Chat recipient) throws IOException;
}
