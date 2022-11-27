package com.chat.client.window.impl;

import com.chat.client.controller.AbstractController;
import com.chat.client.window.AbstractWindow;

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
