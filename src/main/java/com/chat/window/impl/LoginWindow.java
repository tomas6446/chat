package com.chat.window.impl;

import com.chat.controller.AbstractController;
import com.chat.window.AbstractWindow;

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
