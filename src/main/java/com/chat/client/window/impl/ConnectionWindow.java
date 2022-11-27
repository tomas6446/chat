package com.chat.client.window.impl;

import com.chat.client.controller.AbstractController;
import com.chat.client.window.AbstractWindow;

/**
 * @author Tomas Kozakas
 */
public class ConnectionWindow extends AbstractWindow {

    public ConnectionWindow(AbstractController controller) {
        super(controller);
    }

    @Override
    protected String getFxmlPath() {
        return "connection.fxml";
    }

    @Override
    public String getTitle() {
        return "Connection";
    }
}
