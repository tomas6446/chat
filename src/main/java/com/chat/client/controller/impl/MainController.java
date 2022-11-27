package com.chat.client.controller.impl;

import com.chat.client.controller.AbstractController;
import com.chat.client.model.User;
import com.chat.client.view.ViewHandler;
import com.chat.server.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AbstractController {
    private final ObservableList<User> chatList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<User, String> chatCol;
    @FXML
    private TableView<User> chatRooms;

    public MainController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        Server.activeUsers.forEach(activeUser -> System.out.println(activeUser.getUser()));

        chatRooms.setItems(chatList);
    }
}