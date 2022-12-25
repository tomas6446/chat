package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.User;
import com.chat.server.Client;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
@Getter
public class ChatController extends AbstractController {
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

    public ChatController(ViewHandlerImpl viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> back() {
        return e -> viewHandler.getClient().main();
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            Chat chosenChat = row.getItem();
            if (chosenChat != null) {
                viewHandler.getClient().joinRoom(chosenChat.getName());
            }
        };
    }

    private EventHandler<KeyEvent> sendMessage() {
        return e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String msg = viewHandler.getClient().getUser().getName() + ": " + tfInput.getText() + "\n";
                viewHandler.getClient().sendMessage(msg);
                tfInput.clear();
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        Client client = viewHandler.getClient();
        User user = client.getUser();
        Chat chat = user.getChat(client.getChatName());
        if (chat != null) {
            chat.getMessages().forEach(msg -> taOutput.appendText(msg));
        }

        tfRecipient.setText(client.getChatName());
        btnBack.setOnAction(back());

        tfInput.setOnKeyPressed(sendMessage());
        chatTable.setRowFactory(e -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(FXCollections.observableArrayList(user.getAvailableChat()));
    }
}
