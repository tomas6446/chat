package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.User;
import com.chat.server.Client;
import com.chat.view.ViewHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class ChatController extends AbstractController {
    private final User user;
    private final Chat chat;
    private final Client client;
    @FXML
    private TableColumn<String, String> chatCol;
    @FXML
    private TableView<String> chatTable;
    @FXML
    private Button btnBack;
    @FXML
    private TextArea taOutput;
    @FXML
    private TextField tfInput;
    @FXML
    private TextField tfRecipient;

    public ChatController(ViewHandler viewHandler, Client client) {
        super(viewHandler, client);
        this.client = client;
        this.chat = client.getChat();
        this.user = client.getUser();
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchMainWindow(client);
            } catch (IOException ex) {
                System.err.println("Unable to go back to main window");
                ex.printStackTrace();
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<String> row) {
        return e -> {
            if (row != null) {
                client.joinRoom(new Chat(row.getText()));
            }
        };
    }

    private EventHandler<KeyEvent> sendMessage() {
        return e -> {
            if (e.getCode() == KeyCode.ENTER) {

            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfRecipient.setText(chat.getName());

        chatCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        chatTable.setRowFactory(this::handleTable);
        chatTable.setItems(user.getAvailableChat());
        btnBack.setOnAction(back());
        tfInput.setOnKeyPressed(sendMessage());
    }


    private TableRow<String> handleTable(TableView<String> e) {
        TableRow<String> row = new TableRow<>();
        row.setOnMouseClicked(chat(row));
        return row;
    }
}
