package com.chat.app;

import com.chat.app.view.impl.ViewHandlerImpl;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class ChatApplication extends javafx.application.Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        new ViewHandlerImpl(primaryStage).launchLoginWindow();
    }
}
