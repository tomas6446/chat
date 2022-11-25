package com.chat.frontend.view.impl;

import com.chat.frontend.controller.impl.ConnectionController;
import com.chat.frontend.controller.impl.MainController;
import com.chat.frontend.view.ViewHandler;
import com.chat.frontend.window.AbstractWindow;
import com.chat.frontend.window.impl.ConnectionWindow;
import com.chat.frontend.window.impl.MainWindow;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class ViewHandlerImpl implements ViewHandler {
    private final Stage primaryStage;

    public ViewHandlerImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void launchAuthWindow() throws IOException {
        showWindow(new ConnectionWindow(new ConnectionController(this)));
    }

    @Override
    public void launchMainWindow() throws IOException {
        showWindow(new MainWindow(new MainController(this)));
    }

    private void showWindow(AbstractWindow window) throws IOException {
        primaryStage.setTitle(window.getTitle());
        primaryStage.setScene(new Scene(window.root()));
        primaryStage.show();
    }
}
