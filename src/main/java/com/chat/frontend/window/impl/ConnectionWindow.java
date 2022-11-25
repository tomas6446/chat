package com.chat.frontend.window.impl;

import com.chat.frontend.controller.AbstractController;
import com.chat.frontend.window.AbstractWindow;

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
