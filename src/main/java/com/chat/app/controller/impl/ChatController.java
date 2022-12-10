package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.Chat;
import com.chat.app.model.User;
import com.chat.app.view.impl.ViewHandlerImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class ChatController extends AbstractController {
    private final Chat recipient;
    private final ObservableList<Chat> chatTableList;
    private User user;
    private Map<String, User> userMap = new HashMap<>();
    @FXML
    private TableView<Chat> chatTable;
    @FXML
    private Button btnBack;
    @FXML
    private TextArea taOutput;
    @FXML
    private TextField tfInput;
    @FXML
    private TextField tfRecipient;

    public ChatController(ViewHandlerImpl viewHandler, Chat recipient, User user, ObservableList<Chat> chatTableList) {
        super(viewHandler);
        this.recipient = recipient;
        this.chatTableList = chatTableList;
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchMainWindow(user, userMap);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            if (!row.isEmpty()) {
                try {
                    Chat recipient = row.getItem();
                    viewHandler.launchChatWindow(recipient, user, chatTableList);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnBack.setOnAction(back());
        tfRecipient.setText(recipient.getName());
        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(chatTableList);
    }
}
