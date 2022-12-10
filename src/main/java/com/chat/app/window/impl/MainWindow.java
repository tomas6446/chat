package com.chat.app.window.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.window.AbstractWindow;

/**
 * @author Tomas Kozakas
 */
public class MainWindow extends AbstractWindow {
    public MainWindow(AbstractController controller) {
        super(controller);
    }

    @Override
    protected String getFxmlPath() {
        return "main.fxml";
    }

    @Override
    public String getTitle() {
        return "Chat";
    }
}
