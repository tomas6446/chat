package com.chat.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
class InputHandler implements Runnable {
    private final PrintWriter output;
    private final BufferedReader input;
    private List<ClientHandler> clients;

    public InputHandler(List<ClientHandler> clients, PrintWriter output, BufferedReader input) {
        this.output = output;
        this.input = input;
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = input.readLine();
                if (message.equals("/quit")) {
                    break;
                } else {
                    clients.forEach(client -> client.sendMessage(message));

                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    input.close();
                    output.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}