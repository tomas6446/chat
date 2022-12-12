package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.listener.Listener;
import com.chat.app.model.Database;
import com.chat.app.model.User;
import com.chat.app.view.ViewHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class LoginController extends AbstractController {
    private final Database database = new Database();
    private User user = new User();
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
            if (database.containsUser(tfUsername.getText())) {
                User foundUser = database.getUser(tfUsername.getText());
                if (Objects.equals(foundUser.getPassword(), tfPassword.getText())) {
                    try {
                        this.user = foundUser;
                        Listener listener = new Listener(user, database, viewHandler);
                        Thread thread = new Thread(listener);
                        viewHandler.launchMainWindow(user, database);
                        thread.start();
                    } catch (IOException ex) {
                        System.out.println("Error login in");
                        ex.printStackTrace();
                    }
                } else {
                    System.err.println("User not resolved");
                }
            }
        };
    }

    private EventHandler<ActionEvent> register() {
        return e -> {
            if (!database.containsUser(tfUsername.getText())) {
                user = new User(tfUsername.getText(), tfPassword.getText());
                database.addUser(user);
                database.exportData();
                try {
                    viewHandler.launchMainWindow(user, database);
                } catch (IOException ex) {
                    System.err.println("Error launching main window");
                    ex.printStackTrace();
                }
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRegister.setOnAction(register());
        btnConnect.setOnAction(login());
    }
}
