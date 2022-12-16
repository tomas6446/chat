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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class MainController extends AbstractController {
    private final User user;
    private final Listener listener;
    @FXML
    private TableView<Chat> chatTable;
    @FXML
    private TableColumn<Chat, String> chatCol;
    @FXML
    private Label laUsername;
    @FXML
    private TextField tfRecipient;
    @FXML
    private TextField tfNewRoomName;
    @FXML
    private TextField tfRoomName;
    @FXML
    private Button btnChatRoom;
    @FXML
    private Button btnChatUser;
    @FXML
    private Button btnCreateRoom;
    @FXML
    private Button btnLogout;

    public MainController(ViewHandler viewHandler, Listener listener) {
        super(viewHandler, listener);
        this.listener = listener;
        this.user = listener.getUser();
    }

    private EventHandler<ActionEvent> logout() {
        return e -> {
            try {
                viewHandler.launchLoginWindow();
            } catch (IOException ex) {
                System.err.println("Unable to logout");
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            if (row != null) {
                Chat chosenChat = row.getItem();
                try {
                    listener.joinRoom(chosenChat);
                } catch (IOException ex) {
                    System.err.println("Unable to launch chosen chat window");
                }
            }
        };
    }

    private EventHandler<ActionEvent> findUser() {
        return e -> {
            try {
                listener.joinPrivate(tfRecipient.getText());
            } catch (Exception ex) {
                System.err.println("Unable to join a private room");
            }
        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> {
            try {
                listener.joinRoom(new Chat(tfRoomName.getText()));
            } catch (IOException ex) {
                System.err.println("Unable to join a chat room");
            }
        };
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> {
            try {
                listener.createRoom(new Chat(tfNewRoomName.getText()));
            } catch (IOException ex) {
                System.err.println("Unable to create a chat room");
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        laUsername.setText(user.getName());

        btnLogout.setOnAction(logout());
        btnChatUser.setOnAction(findUser());
        btnChatRoom.setOnAction(findRoom());
        btnCreateRoom.setOnAction(createRoom());

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(user.getChatList());
    }
}
