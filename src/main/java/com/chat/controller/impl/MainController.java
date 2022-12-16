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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class MainController extends AbstractController {
    private final User user;
    private final Client client;
    @FXML
    private TableView<String> chatTable;
    @FXML
    private TableColumn<String, String> chatCol;
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

    public MainController(ViewHandler viewHandler, Client client) {
        super(viewHandler, client);
        this.client = client;
        this.user = client.getUser();
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

    private EventHandler<MouseEvent> chat(TableRow<String> row) {
        return e -> {
            if (row != null) {

            }
        };
    }

    private EventHandler<ActionEvent> findUser() {
        return e -> {

        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> {

        };
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> client.createRoom(new Chat(tfNewRoomName.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        laUsername.setText(user.getName());
        btnLogout.setOnAction(logout());
        btnChatUser.setOnAction(findUser());
        btnChatRoom.setOnAction(findRoom());
        btnCreateRoom.setOnAction(createRoom());

        chatCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        chatTable.setRowFactory(this::handleTable);
        chatTable.setItems(user.getAvailableChat());
    }

    private TableRow<String> handleTable(TableView<String> e) {
        TableRow<String> row = new TableRow<>();
        row.setOnMouseClicked(chat(row));
        return row;
    }
}
