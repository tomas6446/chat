package com.chat.frontend.window.impl;

import com.chat.frontend.controller.AbstractController;
import com.chat.frontend.window.AbstractWindow;

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
