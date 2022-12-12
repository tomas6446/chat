package com.chat.app.view.impl;

import com.chat.app.controller.impl.ChatController;
import com.chat.app.controller.impl.LoginController;
import com.chat.app.controller.impl.MainController;
import com.chat.app.model.Chat;
import com.chat.app.model.Database;
import com.chat.app.model.User;
import com.chat.app.view.ViewHandler;
import com.chat.app.window.AbstractWindow;
import com.chat.app.window.impl.ChatWindow;
import com.chat.app.window.impl.LoginWindow;
import com.chat.app.window.impl.MainWindow;
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
    public void launchMainWindow(User user, Database database) throws IOException {
        showWindow(new MainWindow(new MainController(this, user, database)));
    }

    @Override
    public void launchChatWindow(User user, Chat chat, Database database) throws IOException {
        showWindow(new ChatWindow(new ChatController(this, user, chat, database)));
    }

    private void showWindow(AbstractWindow window) throws IOException {
        primaryStage.setTitle(window.getTitle());
        primaryStage.setScene(new Scene(window.root()));
        primaryStage.show();
    }
}
