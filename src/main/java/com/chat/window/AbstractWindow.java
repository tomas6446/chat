package com.chat.window;

import com.chat.controller.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author Tomas Kozakas
 */
public abstract class AbstractWindow {
    private final AbstractController controller;

    protected AbstractWindow(AbstractController controller) {
        this.controller = controller;
    }

    public Parent root() throws IOException {
        FXMLLoader loader = new FXMLLoader(url());
        loader.setController(controller);
        return loader.load();
    }

    private URL url() {
        return getClass().getClassLoader().getResource(getFxmlPath());
    }

    protected abstract String getFxmlPath();

    public abstract String getTitle();
}
