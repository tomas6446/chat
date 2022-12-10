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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileWriter;
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
    private ObservableList<Chat> chatObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Chat> chatTable;
    @FXML
    private TableColumn<Chat, String> chatCol;

    private Map<String, Chat> chatRooms = new HashMap<>();
    private final User user;

    public MainController(ViewHandlerImpl viewHandler, User user) {
        super(viewHandler);
        this.user = user;
    }

    public void exportData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Chat> chatRoomList = chatRooms.values().stream().toList();
        objectMapper.writeValue(new FileWriter("data/chatroom.json"), chatRoomList);
    }

    public void importData() {
        List<ChatRoom> chatRoomList;
        try {
            chatRoomList = new ObjectMapper().readValue(new File("data/chatroom.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        chatRooms = chatRoomList.stream().collect(Collectors.toMap(ChatRoom::getName, Function.identity()));
        chatObservableList = FXCollections.observableArrayList(chatRooms.values().stream().toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        importData();
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        System.out.println("Connected user:" + user);

        chatTable.setItems(chatObservableList);
    }
}
