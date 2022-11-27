package com.chat.client.controller.impl;

import com.chat.client.controller.AbstractController;
import com.chat.client.model.User;
import com.chat.client.view.ViewHandler;
import com.chat.server.Client;
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
            try {
                new Client(new User(tfUser.getText(), "127.01.01", 5000));

            } catch (IOException e) {
                System.out.println("Unable to connect user: " + tfUser.getText());
            }
        });
    }
}