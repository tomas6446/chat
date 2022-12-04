package com.chat.server;

import com.chat.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * @author Tomas Kozakas
 */
public class Connection implements Runnable {
    private final Socket socket;
    private final Server server;
    private Menu menu;

    public Connection(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.menu = new Menu(server, new BufferedReader(new InputStreamReader(socket.getInputStream())), new PrintWriter(socket.getOutputStream(), true));
    }

    @Override
    public void run() {
        try {
            menu.run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            server.shutDown();
        }
    }

    public void sendMessage(String message) {
        menu.println(message);
    }

    public User getUser() {
        return menu.getUser();
    }
}