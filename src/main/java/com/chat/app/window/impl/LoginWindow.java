package com.chat.app.window.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.window.AbstractWindow;

/**
 * @author Tomas Kozakas
 */
public class LoginWindow extends AbstractWindow {
    public LoginWindow(AbstractController controller) {
        super(controller);
    }

    @Override
    protected String getFxmlPath() {
        return "login.fxml";
    }

    @Override
    public String getTitle() {
        return "Connection";
    }
}
