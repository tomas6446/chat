package com.chat.frontend.controller;

import com.chat.frontend.view.ViewHandler;
import javafx.fxml.Initializable;

/**
 * @author Tomas Kozakas
 */
public abstract class AbstractController implements Initializable {
    protected final ViewHandler viewHandler;

    protected AbstractController(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }
}
