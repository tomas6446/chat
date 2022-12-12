package com.chat.server.client;

import com.chat.app.view.impl.ViewHandlerImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Tomas Kozakas
 */
public class ClientApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        new ViewHandlerImpl(stage).launchLoginWindow();
    }
}
