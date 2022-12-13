package com.chat.server;

import com.chat.model.Message;
import com.chat.model.MessageType;
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
    private final User user;
    private ViewHandler viewHandler;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Listener(User user, ViewHandler viewHandler) {
        this.user = user;
        this.viewHandler = viewHandler;
    }

    @Override
    public void run() {
        try {
            try (Socket socket = new Socket("127.0.0.1", 5000)) {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                connect();

                Message message;
                while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                    System.out.println(message);
                    switch (message.getMessageType()) {
                        case LOGIN -> viewHandler.launchMainWindow();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not connect to server");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        outputStream.writeObject(new Message(user, MessageType.CONNECT));
    }

    public void sendMessage() {

    }

    public void acceptMessage() {

    }
}
