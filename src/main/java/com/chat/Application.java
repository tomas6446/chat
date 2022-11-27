package com.chat;

import com.chat.client.view.impl.ViewHandlerImpl;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        new ViewHandlerImpl(primaryStage).launchConnectionWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}