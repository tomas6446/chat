package com.chat.window.impl;

import com.chat.controller.AbstractController;
import com.chat.window.AbstractWindow;

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
