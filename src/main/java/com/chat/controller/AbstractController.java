package com.chat.controller;

import com.chat.server.Client;
import com.chat.view.ViewHandler;
import com.chat.view.impl.ViewHandlerImpl;
import javafx.fxml.Initializable;

/**
 * @author Tomas Kozakas
 */
public abstract class AbstractController implements Initializable {
    protected final ViewHandlerImpl viewHandler;

    protected AbstractController(ViewHandlerImpl viewHandler) {
        this.viewHandler = viewHandler;
    }
}
