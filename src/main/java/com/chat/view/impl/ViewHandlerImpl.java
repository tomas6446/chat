package com.chat.view.impl;

import com.chat.controller.impl.ChatController;
import com.chat.controller.impl.LoginController;
import com.chat.controller.impl.MainController;
import com.chat.view.ViewHandler;
import com.chat.window.AbstractWindow;
import com.chat.window.impl.ChatWindow;
import com.chat.window.impl.LoginWindow;
import com.chat.window.impl.MainWindow;
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
    public void launchLoginWindow() throws IOException {
        showWindow(new LoginWindow(new LoginController(this)));
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
        primaryStage.setScene(new Scene(window.root()));
        primaryStage.setTitle(window.getTitle());
        primaryStage.show();
    }
}
