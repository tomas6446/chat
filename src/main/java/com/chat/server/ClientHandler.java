package com.chat.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public ClientHandler(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {

    }
}
