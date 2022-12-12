package com.chat.server;

import com.chat.model.Database;
import com.chat.model.Message;
import com.chat.model.User;
import com.chat.view.ViewHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class Listener implements Runnable {
    private Socket socket;
    private final User user;
    private final Database database;
    private final ViewHandler viewHandler;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Listener(User user, Database database, ViewHandler viewHandler) {
        this.user = user;
        this.database = database;
        this.viewHandler = viewHandler;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);

            System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
            connect();
        } catch (IOException e) {
            System.err.println("Could not connect to server");
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        System.out.println("Successfully connected");
        Message message = new Message("user connected");
        System.out.println(message);
    }
}
