package com.chat.frontend.controller.impl;

import com.chat.backend.client.Client;
import com.chat.backend.server.Server;
import com.chat.frontend.controller.AbstractController;
import com.chat.frontend.view.ViewHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController extends AbstractController {
    @FXML
    private Button btnConnect;
    @FXML
    private TextField tfUser;

    public ConnectionController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConnect.setOnMouseClicked(event -> {
            new Client("127.01.01", 5000);
            try {
                viewHandler.launchMainWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}