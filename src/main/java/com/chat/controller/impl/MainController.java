package com.chat.controller.impl;

import com.chat.controller.AbstractController;
import com.chat.model.Chat;
import com.chat.model.User;
import com.chat.server.Client;
import com.chat.view.ViewHandler;
import javafx.collections.FXCollections;
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
    private TextField tfNewRoomName;
    @FXML
    private TextField tfRoomName;
    @FXML
    private Button btnChatRoom;
    @FXML
    private Button btnCreateRoom;
    @FXML
    private Button btnLogout;

    public MainController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    private EventHandler<ActionEvent> logout() {
        return e -> viewHandler.getClient().auth();
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            Chat chosenChat = row.getItem();
            if (chosenChat != null) {
                viewHandler.getClient().joinRoom(chosenChat.getName());
            }
        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> viewHandler.getClient().joinRoom(tfRoomName.getText());
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> viewHandler.getClient().createRoom(tfNewRoomName.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        Client client = viewHandler.getClient();
        User user = client.getUser();

        laUsername.setText(user.getName());
        btnLogout.setOnAction(logout());
        btnChatRoom.setOnAction(findRoom());
        btnCreateRoom.setOnAction(createRoom());

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(FXCollections.observableArrayList(user.getAvailableChat()));
    }
}
