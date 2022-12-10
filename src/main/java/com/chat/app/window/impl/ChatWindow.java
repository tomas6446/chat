package com.chat.app.window.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.window.AbstractWindow;

/**
 * @author Tomas Kozakas
 */
public class ChatWindow extends AbstractWindow {

    public ChatWindow(AbstractController controller) {
        super(controller);
    }

    @Override
    protected String getFxmlPath() {
        return "chat.fxml";
    }

    @Override
    public String getTitle() {
        return "Chat";
    }
}
