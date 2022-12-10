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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class RegisterController extends AbstractController {
    private User user;
    private Map<String, User> userMap = new HashMap<>();
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRegister;

    public RegisterController(ViewHandler viewHandler, Map<String, User> userMap) {
        super(viewHandler);
        this.userMap = userMap;
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            if (!userMap.containsKey(tfUsername.getText())) {
                user = new User(tfUsername.getText(), tfPassword.getText());
                userMap.put(tfUsername.getText(), user);
                exportData();
                try {
                    viewHandler.launchMainWindow(user, userMap);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        };
    }

    private void exportData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<User> userList = userMap.values().stream().toList();
            objectMapper.writeValue(new FileWriter("data/users.json"), userList);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchLoginWindow();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(back());
        btnRegister.setOnAction(register());
    }
}
