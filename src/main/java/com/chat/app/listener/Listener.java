package com.chat.app.listener;

import com.chat.app.model.Database;
import com.chat.app.model.User;
import com.chat.app.view.ViewHandler;

import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class Listener implements Runnable {
    private Socket socket;
    private final User user;
    private final Database database;
    private final ViewHandler viewHandler;

    public Listener(User user, Database database, ViewHandler viewHandler) {
        this.user = user;
        this.database = database;
        this.viewHandler = viewHandler;
    }

    @Override
    public void run() {
        System.out.println("Listening to " + user);
        try {
            socket = new Socket();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
