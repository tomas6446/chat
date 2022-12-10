package com.chat.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private List<ClientHandler> clients;
    private PrintWriter output;
    private BufferedReader input;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new InputHandler(clients, output, input).run();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
