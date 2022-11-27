package com.chat.client.view.impl;

import com.chat.client.controller.impl.ChatController;
import com.chat.client.controller.impl.ConnectionController;
import com.chat.client.controller.impl.MainController;
import com.chat.client.view.ViewHandler;
import com.chat.client.window.AbstractWindow;
import com.chat.client.window.impl.ChatWindow;
import com.chat.client.window.impl.ConnectionWindow;
import com.chat.client.window.impl.MainWindow;
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
    public void launchConnectionWindow() throws IOException {
        showWindow(new ConnectionWindow(new ConnectionController(this)));
    }

    @Override
    public void launchMainWindow() throws IOException {
        showWindow(new MainWindow(new MainController(this)));
    }

    @Override
    public void launchChatWindow() throws IOException {
        showWindow(new ChatWindow(new ChatController(this)));
    }

    private void showWindow(AbstractWindow window) throws IOException {
        primaryStage.setTitle(window.getTitle());
        primaryStage.setScene(new Scene(window.root()));
        primaryStage.show();
    }
}
