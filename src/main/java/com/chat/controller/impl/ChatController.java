package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.User;
import com.chat.server.Listener;
import com.chat.view.ViewHandler;
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
    private final Listener listener;
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

    public ChatController(ViewHandler viewHandler, Listener listener) {
        super(viewHandler, listener);
        this.listener = listener;
        this.user = listener.getUser();
        this.chat = listener.getChat();
    }

    private EventHandler<ActionEvent> back() {
        return e -> {
            try {
                viewHandler.launchMainWindow(listener);
            } catch (IOException ex) {
                System.err.println("Unable to go back to main window");
                ex.printStackTrace();
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            try {
                if (row != null) {
                    listener.joinRoom(row.getItem());
                }
            } catch (IOException ex) {
                System.err.println("Unable to join a chat room");
                ex.printStackTrace();
            }
        };
    }

    private EventHandler<KeyEvent> sendMessage() {
        return e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    String sendMessage = user.getName() + ": " + tfInput.getText() + "\n";
                    taOutput.appendText(sendMessage);
                    listener.sendMessage(chat, sendMessage);

                    tfInput.clear();
                } catch (IOException ex) {
                    System.err.println("Unable to send a message");
                    ex.printStackTrace();
                }
            }
        };
    }

    public void receive(String message) {
        if(taOutput != null) {
            taOutput.appendText(message);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chat.getMessages().forEach(msg -> taOutput.appendText(msg));
        tfRecipient.setText(chat.getName());

        chatTable.setRowFactory(e -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(user.getChatList());
        btnBack.setOnAction(back());
        tfInput.setOnKeyPressed(sendMessage());
    }


}
