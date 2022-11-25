module com.chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.chat to javafx.fxml;
    exports com.chat;
    exports com.chat.backend.server;
    opens com.chat.backend.server to javafx.fxml;
    exports com.chat.view;
    opens com.chat.view to javafx.fxml;
}