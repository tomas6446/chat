package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Message;
import com.chat.model.MessageType;
import com.chat.model.User;
import com.chat.server.Client;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class LoginController extends AbstractController {
    private User user;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnConnect;

    public LoginController(ViewHandlerImpl viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> login() {
        return e -> {
            user = new User(tfUsername.getText(), tfPassword.getText());
            Client client = new Client(user, viewHandler, new Message(user, MessageType.LOGIN));
            viewHandler.setClient(client);
            Thread thread = new Thread(client);
            thread.start();
        };
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            user = new User(tfUsername.getText(), tfPassword.getText());
            Client client = new Client(user, viewHandler, new Message(user, MessageType.REGISTER));
            viewHandler.setClient(client);
            Thread thread = new Thread(client);
            thread.start();
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRegister.setOnAction(register());
        btnConnect.setOnAction(login());
    }
}
