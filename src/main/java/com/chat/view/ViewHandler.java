package com.chat.view;

import com.chat.controller.impl.ChatController;
import com.chat.controller.impl.LoginController;
import com.chat.controller.impl.MainController;
import com.chat.server.Client;
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
public class ViewHandler {
    private final Stage primaryStage;
    private final LoginController loginController;
    private final MainController mainController;
    private final ChatController chatController;
    private Client client;
    private MainWindow mainWindow;
    private LoginWindow loginWindow;
    private ChatWindow chatWindow;

    public ViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginController = new LoginController(this);
        this.mainController = new MainController(this);
        this.chatController = new ChatController(this);

        this.loginWindow = new LoginWindow(loginController);
        this.mainWindow = new MainWindow(mainController);
        this.chatWindow = new ChatWindow(chatController);
    }


    public void launchLoginWindow() {
        showWindow(loginWindow);
    }

    public void launchMainWindow() {
        showWindow(mainWindow);
    }

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
