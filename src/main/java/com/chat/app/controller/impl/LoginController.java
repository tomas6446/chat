package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.User;
import com.chat.app.view.ViewHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tomas Kozakas
 */
public class LoginController extends AbstractController {
    private Map<String, User> users = new HashMap<>();
    private User user;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnRegister;

    public LoginController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> login() {
        return e -> {
            if (users.containsKey(tfUsername.getText())) {
                User foundUser = users.get(tfUsername.getText());
                if (Objects.equals(foundUser.getPassword(), tfPassword.getText())) {
                    this.user = foundUser;
                    try {
                        viewHandler.launchMainWindow(user);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            try {
                viewHandler.launchRegisterWindow(users);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void importData() {
        try {
            List<User> userList = new ObjectMapper().readValue(new File("data/users.json"), new TypeReference<>() {
            });
            users = userList.stream().collect(Collectors.toMap(User::getName, Function.identity()));
        } catch (IOException e) {
            System.err.println("Unable to import user list");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        importData();
        btnConnect.setOnAction(login());
        btnRegister.setOnAction(register());
    }
}
