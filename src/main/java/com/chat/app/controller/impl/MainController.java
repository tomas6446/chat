package com.chat.app.controller.impl;

import com.chat.app.controller.AbstractController;
import com.chat.app.model.Chat;
import com.chat.app.model.ChatRoom;
import com.chat.app.model.User;
import com.chat.app.view.impl.ViewHandlerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class MainController extends AbstractController {
    private final User user;
    private final Map<String, User> userMap;
    private Map<String, ChatRoom> chatRoomMap;
    private ObservableList<Chat> chatTableList = FXCollections.observableArrayList();
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


    public MainController(ViewHandlerImpl viewHandler, User user, Map<String, User> userMap) {
        super(viewHandler);
        this.user = user;
        this.userMap = userMap;
    }

    private void importData() {
        chatTableList.addAll(user.getOngoingMessages().values().stream().toList());
    }

    private void exportData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ChatRoom> chatRoomList = chatRoomMap.values().stream().toList();
            objectMapper.writeValue(new FileWriter("data/chatroom.json"), chatRoomList);

            List<User> userList = userMap.values().stream().toList();
            objectMapper.writeValue(new FileWriter("data/users.json"), userList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private EventHandler<ActionEvent> logout() {
        return e -> {
            try {
                viewHandler.launchLoginWindow();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        };
    }

    private EventHandler<MouseEvent> chat(TableRow<Chat> row) {
        return e -> {
            if (!row.isEmpty()) {
                try {
                    Chat recipient = row.getItem();
                    viewHandler.launchChatWindow(recipient, user, chatTableList);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }

    private EventHandler<ActionEvent> findUser() {
        return e -> {
//            try {
//                String name = tfRecipient.getText();
//                if (userMap.containsKey(name)) {
//                    //viewHandler.launchChatWindow(userMap.get(name)));
//                }
//            } catch (IOException ex) {
//                System.err.println(ex.getMessage());
//            }
        };
    }

    private EventHandler<ActionEvent> findRoom() {
        return e -> {
            try {
                String name = tfRoomName.getText();
                if (chatRoomMap.containsKey(name)) {
                    viewHandler.launchChatWindow(chatRoomMap.get(name), user, chatTableList);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> createRoom() {
        return e -> {
            String name = tfNewRoomName.getText();
            if (!chatRoomMap.containsKey(name)) {
                ChatRoom newChatRoom = new ChatRoom(name);
                user.getChatRoomNames().add(name);
                chatRoomMap.put(name, newChatRoom);
                chatTableList.add(newChatRoom);
                chatTable.setItems(chatTableList);
                exportData();
                try {
                    viewHandler.launchChatWindow(chatRoomMap.get(name), user, chatTableList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        laUsername.setText(user.getName());
        importData();

        btnLogout.setOnAction(logout());
        btnChatUser.setOnAction(findUser());
        btnChatRoom.setOnAction(findRoom());
        btnCreateRoom.setOnAction(createRoom());

        chatTable.setRowFactory(chat -> {
            TableRow<Chat> row = new TableRow<>();
            row.setOnMouseClicked(chat(row));
            return row;
        });
        chatTable.setItems(chatTableList);
    }
}
