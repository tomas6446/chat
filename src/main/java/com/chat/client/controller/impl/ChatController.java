package com.chat.client.controller.impl;

import com.chat.client.controller.AbstractController;
import com.chat.client.view.ViewHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Kozakas
 */
public class ChatController extends AbstractController {
    @FXML
    private TextField tfInput;
    @FXML
    private TextField taOutput;

    public ChatController(ViewHandler viewHandler) {
        super(viewHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
