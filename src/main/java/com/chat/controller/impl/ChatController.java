package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.User;
import com.chat.server.Client;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<Chat, String> chatCol;
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

    public ChatController(ViewHandlerImpl viewHandler, Client client) {
        super(viewHandler, client);
        this.client = client;
        this.chat = client.getChat();
        this.user = client.getUser();
    }

    private EventHandler<ActionEvent> back() {
        return e -> client.main();
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            Chat chosenChat = row.getItem();
            client.joinRoom(chosenChat);
        };
    }

    private EventHandler<KeyEvent> sendMessage() {
        return e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String message = user.getName() + ": " + tfInput.getText() + "\n";
                taOutput.appendText(message);
                client.sendMessage(message);
                tfInput.clear();
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chat.getMessages().forEach(msg -> taOutput.appendText(msg));
        tfRecipient.setText(chat.getName());
        btnBack.setOnAction(back());

        tfInput.setOnKeyPressed(sendMessage());

        chatTable.setRowFactory(e -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(user.getChatList());
    }
}
