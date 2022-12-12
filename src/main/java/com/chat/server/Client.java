package com.chat.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class Client {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private List<String> messages = new ArrayList<>();

    public Client() {
        try {
            socket = new Socket("127.01.01", 5000);

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
