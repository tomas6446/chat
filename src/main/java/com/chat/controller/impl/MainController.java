package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.Database;
import com.chat.model.User;
import com.chat.view.impl.ViewHandlerImpl;
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
    private final Database database;
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

    public MainController(ViewHandlerImpl viewHandler, User user, Database database) {
        super(viewHandler);
        this.user = user;
        this.database = database;
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
            Chat chosenChat = row.getItem();
            try {
                viewHandler.launchChatWindow(user, chosenChat, database);
            } catch (IOException ex) {
                System.err.println("Unable to launch chosen chat window");
            }
        };
    }

    private EventHandler<ActionEvent> findUser() {
        return e -> {
            if (database.containsUser(tfRoomName.getText())) {

            }
        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> {
            if (database.containsChat(tfRoomName.getText())) {
                Chat chat = database.getChat(tfRoomName.getText());
                if (!user.getChatList().contains(chat)) {
                    user.addChat(chat);
                    database.updateUser(user);
                    database.exportData();
                    try {
                        viewHandler.launchChatWindow(user, chat, database);
                    } catch (IOException ex) {
                        System.err.println("Unable to launch chosen chat window");
                    }
                }

            }
        };
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> {
            if (!database.containsChat(tfNewRoomName.getText())) {
                Chat newChat = new Chat(tfNewRoomName.getText());
                user.addChat(newChat);
                database.addChat(newChat);
                database.updateUser(user);
                database.exportData();
                chatTable.setItems(user.getChatList());
                try {
                    viewHandler.launchChatWindow(user, newChat, database);
                } catch (IOException ex) {
                    System.err.println("Unable to create a chat room");
                }
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
