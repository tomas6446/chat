package com.chat.window.impl;

import com.chat.controller.AbstractController;
import com.chat.window.AbstractWindow;

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
