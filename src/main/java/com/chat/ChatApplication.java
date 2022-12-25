package com.chat;

import com.chat.view.impl.ViewHandlerImpl;
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
        new ViewHandlerImpl(primaryStage).launchLoginWindow();
    }
}
