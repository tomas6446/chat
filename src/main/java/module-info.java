module com.chat {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.chat.server;
    opens com.chat.server to javafx.fxml;
    exports com.chat.client.controller;
    opens com.chat.client.controller to javafx.fxml;
    exports com.chat;
    opens com.chat to javafx.fxml;
    exports com.chat.client.controller.impl;
    opens com.chat.client.controller.impl to javafx.fxml;
    exports com.chat.client.window;
    opens com.chat.client.window to javafx.fxml;
}