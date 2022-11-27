package com.chat.server;

import com.chat.client.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class Client {
    public Client(User user) throws IOException {
        System.out.println("Attempting to connect a user...");
        InetAddress ip = InetAddress.getByName(user.getAddress());
        ObjectOutputStream objectOutputStream;
        try (Socket socket = new Socket(ip, user.getPort())) {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(user);
            System.out.println("Connecting " + user);
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(new User("username", "127.0.0.1", 5000));
    }

}
