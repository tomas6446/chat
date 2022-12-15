package com.chat.view.impl;

import com.chat.controller.impl.ChatController;
import com.chat.controller.impl.LoginController;
import com.chat.controller.impl.MainController;
import com.chat.model.Chat;
import com.chat.server.Listener;
import com.chat.view.ViewHandler;
import com.chat.window.AbstractWindow;
import com.chat.window.impl.ChatWindow;
import com.chat.window.impl.LoginWindow;
import com.chat.window.impl.MainWindow;
import javafx.application.Platform;
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
    public void launchLoginWindow() {
        showWindow(new LoginWindow(new LoginController(this)));
    }

    @Override
    public void launchMainWindow(Listener listener) {
        showWindow(new MainWindow(new MainController(this, listener)));
    }

    @Override
    public void launchChatWindow(Listener listener, Chat chat) {
        showWindow(new ChatWindow(new ChatController(this, listener, chat)));
    }

    private void showWindow(AbstractWindow window) {
        Platform.runLater(() -> {
            try {
                primaryStage.setScene(new Scene(window.root()));
                primaryStage.setTitle(window.getTitle());
                primaryStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
