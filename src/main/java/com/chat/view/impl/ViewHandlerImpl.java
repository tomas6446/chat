package com.chat.view.impl;

import com.chat.controller.impl.ChatController;
import com.chat.controller.impl.LoginController;
import com.chat.controller.impl.MainController;
import com.chat.server.Client;
import com.chat.view.ViewHandler;
import com.chat.window.AbstractWindow;
import com.chat.window.impl.ChatWindow;
import com.chat.window.impl.LoginWindow;
import com.chat.window.impl.MainWindow;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
public class ViewHandlerImpl implements ViewHandler {
    private final Stage primaryStage;
    private final LoginController loginController;
    private final MainController mainController;
    private final ChatController chatController;
    private Client client;

    public ViewHandlerImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginController = new LoginController(this);
        this.mainController = new MainController(this);
        this.chatController = new ChatController(this);
    }

    @Override
    public void launchLoginWindow() {
        showWindow(new LoginWindow(loginController));
    }

    @Override
    public void launchMainWindow() {
        showWindow(new MainWindow(mainController));
    }

    @Override
    public void launchChatWindow() {
        showWindow(new ChatWindow(chatController));
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
