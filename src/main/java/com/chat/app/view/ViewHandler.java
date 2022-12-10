package com.chat.app.view;

import com.chat.app.model.Chat;
import com.chat.app.model.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow(User user, Map<String, User> userMap) throws IOException;

    void launchRegisterWindow(Map<String, User> userMap) throws IOException;

    void launchChatWindow(Chat recipient, User user, ObservableList<Chat> chatTableList) throws IOException;
}
