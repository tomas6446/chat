package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.User;
import com.chat.app.view.ViewHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class RegisterController extends AbstractController {
    private final Map<String, User> users;
    private User user;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRegister;

    public RegisterController(ViewHandler viewHandler, Map<String, User> users) {
        super(viewHandler);
        this.users = users;
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            if (!users.containsKey(tfUsername.getText())) {
                user = new User(tfUsername.getText(), tfPassword.getText());
                users.put(tfUsername.getText(), user);
                exportData();
                try {
                    viewHandler.launchMainWindow(user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    private void exportData() {
        List<User> userList = users.values().stream().toList();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileWriter("data/users.json"), userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchLoginWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(back());
        btnRegister.setOnAction(register());
    }
}
