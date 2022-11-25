package com.chat;

import com.chat.frontend.view.impl.ViewHandlerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        new ViewHandlerImpl(primaryStage).launchAuthWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}