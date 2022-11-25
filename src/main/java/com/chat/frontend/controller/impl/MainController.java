package com.chat.frontend.controller.impl;

import com.chat.backend.client.Client;
import com.chat.backend.clientHandler.ClientHandler;
import com.chat.backend.server.Server;
import com.chat.frontend.controller.AbstractController;
import com.chat.frontend.view.ViewHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AbstractController {
    private final ObservableList<ClientHandler> chatList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<ClientHandler, String> chatCol;
    @FXML
    private TableView<ClientHandler> chatRooms;
    @FXML
    private TextField tfInput;
    @FXML
    private TextArea taOutput;

    public MainController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        chatRooms.setItems(chatList);
    }
}