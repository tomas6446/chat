module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    exports com.chat;
    opens com.chat.controller to
            javafx.fxml;
    opens com.chat.controller.impl to
            javafx.fxml;
    opens com.chat.window to
            javafx.fxml;
    opens com.chat.view to
            javafx.fxml;

    exports com.chat.model;
    opens com.chat.model to
            lombok,
            com.fasterxml.jackson.annotation,
            com.fasterxml.jackson.databind,
            javafx.fxml;

}