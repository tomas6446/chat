package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.User;
import com.chat.server.Listener;
import com.chat.view.ViewHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class LoginController extends AbstractController {
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnConnect;

    public LoginController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> login() {
        return e -> {
            Listener listener = new Listener(new User(tfUsername.getText(), tfPassword.getText()), viewHandler);
            Thread thread = new Thread(listener);
            thread.start();
        };
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            try {
                viewHandler.launchMainWindow();
            } catch (IOException ex) {
                System.err.println("Unable to launch main menu");
                ex.printStackTrace();
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRegister.setOnAction(register());
        btnConnect.setOnAction(login());
    }
}
