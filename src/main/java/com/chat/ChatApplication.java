package com.chat;

import com.chat.view.ViewHandler;
import javafx.stage.Stage;

/**
 * @author Tomas Kozakas
 */
public class ChatApplication extends javafx.application.Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        new ViewHandler(primaryStage).launchLoginWindow();
    }
}
