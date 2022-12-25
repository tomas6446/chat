package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class MainController extends AbstractController {
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

    public MainController(ViewHandlerImpl viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> logout() {
        return e -> viewHandler.getClient().auth();
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            Chat chosenChat = row.getItem();
            if (chosenChat != null) {
                viewHandler.getClient().joinRoom(chosenChat);
            }
        };
    }

    private EventHandler<ActionEvent> findUser() {
        return e -> {

        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> viewHandler.getClient().joinRoom(new Chat(tfRoomName.getText()));
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> viewHandler.getClient().createRoom(new Chat(tfNewRoomName.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        laUsername.setText(viewHandler.getClient().getUser().getName());

        btnLogout.setOnAction(logout());
        btnChatUser.setOnAction(findUser());
        btnChatRoom.setOnAction(findRoom());
        btnCreateRoom.setOnAction(createRoom());

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(viewHandler.getClient().getUser().getAvailableChat());
    }
}
