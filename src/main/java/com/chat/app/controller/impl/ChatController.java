package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.Chat;
import com.chat.app.model.Database;
import com.chat.app.model.User;
import com.chat.app.view.impl.ViewHandlerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private final Database database;
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

    public ChatController(ViewHandlerImpl viewHandler, User user, Chat chat, Database database) {
        super(viewHandler);
        this.user = user;
        this.chat = chat;
        this.database = database;
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchMainWindow(user, database);
            } catch (IOException ex) {
                System.err.println("Unable to go back");
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            Chat chosenChat = row.getItem();
            try {
                viewHandler.launchChatWindow(user, chosenChat, database);
            } catch (IOException ex) {
                System.err.println("Unable to launch chosen chat window");
            }
        };
    }

    private EventHandler<KeyEvent> sendMessage() {
        return e -> {
            if (e.getCode() == KeyCode.ENTER) {
                chat.getMessages().add(user.getName() + ": " + tfInput.getText() + "\n");
                taOutput.appendText(user.getName() + ": " + tfInput.getText() + "\n");
                database.updateChat(chat);
                database.exportData();
                tfInput.clear();
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        database.getChatMessages(chat.getName()).forEach(msg -> taOutput.appendText(msg));
        tfRecipient.setText(chat.getName());
        btnBack.setOnAction(back());

        tfInput.setOnKeyPressed(sendMessage());

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(user.getChatList());
    }
}
