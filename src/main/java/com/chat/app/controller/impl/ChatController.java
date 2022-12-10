package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.Chat;
import com.chat.app.model.User;
import com.chat.app.view.impl.ViewHandlerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class ChatController extends AbstractController {
    private Chat recipient;
    private User user;

    @FXML
    private Button btnBack;
    @FXML
    private TextArea taOutput;
    @FXML
    private TextField tfInput;
    @FXML
    private TextField tfRecipient;
    @FXML
    private TableView<Chat> chatTable;

    public ChatController(ViewHandlerImpl viewHandler, User user, Chat recipient) {
        super(viewHandler);
        this.user = user;
        this.recipient = recipient;
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchMainWindow(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnBack.setOnAction(back());
        tfRecipient.setText(recipient.getName());
    }
}
