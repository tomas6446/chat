package com.chat.server;

import com.chat.model.Message;
import com.chat.model.MessageType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * @author Tomas Kozakas
 */
public class Handler extends Thread {
    private final Socket socket;
    private final Validate validate = new Validate();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            System.out.println("A new client is connected: " + socket);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Message message;
            while (socket.isConnected() && (message = (Message) inputStream.readObject()) != null) {
                System.out.println(message);
                if (Objects.requireNonNull(message.getMessageType()) == MessageType.CONNECT) {
                    if (validate.connect(message.getUser())) {
                        outputStream.writeObject(new Message(message.getUser(), MessageType.LOGIN));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }
}
