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
    private Client client;
    private final Stage primaryStage;
    private final LoginController loginController;
    private final MainController mainController;
    private final ChatController chatController;

    private MainWindow mainWindow;
    private LoginWindow loginWindow;
    private ChatWindow chatWindow;

    public ViewHandlerImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginController = new LoginController(this);
        this.mainController = new MainController(this);
        this.chatController = new ChatController(this);

        this.loginWindow = new LoginWindow(loginController);
        this.mainWindow = new MainWindow(mainController);
        this.chatWindow = new ChatWindow(chatController);
    }

    @Override
    public void launchLoginWindow() {
        showWindow(loginWindow);
    }

    @Override
    public void launchMainWindow() {
        showWindow(mainWindow);
    }

    @Override
    public void launchChatWindow() {
        showWindow(chatWindow);
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
