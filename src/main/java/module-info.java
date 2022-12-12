module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    exports com.chat.app;
    opens com.chat.app.controller to
            javafx.fxml;
    opens com.chat.app.controller.impl to
            javafx.fxml;
    opens com.chat.app.window to
            javafx.fxml;
    opens com.chat.app.view to
            javafx.fxml;

    exports com.chat.app.model;
    opens com.chat.app.model to
            lombok,
            com.fasterxml.jackson.annotation,
            com.fasterxml.jackson.databind,
            javafx.fxml;

    exports com.chat.server.server;
    opens com.chat.server.server to
            lombok,
            com.fasterxml.jackson.annotation,
            com.fasterxml.jackson.databind;

    exports com.chat.server.model;
    opens com.chat.server.model to
            lombok,
            com.fasterxml.jackson.annotation,
            com.fasterxml.jackson.databind;

    exports com.chat.server.client;
    opens com.chat.server.client to
            lombok,
            com.fasterxml.jackson.annotation,
            com.fasterxml.jackson.databind;
}