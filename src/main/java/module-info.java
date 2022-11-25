module com.chat {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.chat.backend.server;
    opens com.chat.backend.server to javafx.fxml;
    exports com.chat.frontend.controller;
    opens com.chat.frontend.controller to javafx.fxml;
    exports com.chat;
    opens com.chat to javafx.fxml;
    exports com.chat.frontend.controller.impl;
    opens com.chat.frontend.controller.impl to javafx.fxml;
}