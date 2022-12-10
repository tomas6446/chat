package com.chat.app.window.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.window.AbstractWindow;

/**
 * @author Tomas Kozakas
 */
public class RegisterWindow extends AbstractWindow {
    public RegisterWindow(AbstractController controller) {
        super(controller);
    }

    @Override
    protected String getFxmlPath() {
        return "register.fxml";
    }

    @Override
    public String getTitle() {
        return "Register";
    }
}
