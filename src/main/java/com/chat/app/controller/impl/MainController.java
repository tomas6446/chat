package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.Chat;
import com.chat.app.model.ChatRoom;
import com.chat.app.model.User;
import com.chat.app.view.impl.ViewHandlerImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tomas Kozakas
 */
public class MainController extends AbstractController {
    private final User user;
    @FXML
    private TableView<Chat> chatTable;
    @FXML
    private TableColumn<Chat, String> chatCol;


    private ObservableList<Chat> chatList = FXCollections.observableArrayList();
    private Map<String, Chat> chatRooms = new HashMap<>();

    public MainController(ViewHandlerImpl viewHandler, User user) {
        super(viewHandler);
        this.user = user;
    }

    private void importData() {
        try {
            List<ChatRoom> roomList = new ObjectMapper().readValue(new File("data/chatroom.json"), new TypeReference<>() {
            });
            chatList = FXCollections.observableArrayList(roomList);
            chatList.addAll(user.getOngoingMessages().values().stream().toList());
            chatRooms = roomList.stream().collect(Collectors.toMap(ChatRoom::getName, Function.identity()));
        } catch (IOException e) {
            System.err.println("Unable to import chat room list");
        }
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            if (!row.isEmpty()) {
                try {
                    Chat recipient = row.getItem();
                    viewHandler.launchChatWindow(user, recipient);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        importData();

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(chatList);
    }
}
