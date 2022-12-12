package com.chat.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Tomas Kozakas
 */
class InputHandler implements Runnable {
    private final PrintWriter output;
    private final Client client;

    public InputHandler(Client client, PrintWriter output) {
        this.client = client;
        this.output = output;
    }

    @Override
    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        boolean done = false;
        while (!done) {
            try {
                String message = input.readLine();
                if (message.equals("/quit")) {
                    output.println(message);
                    input.close();
                    output.close();
                    client.shutDown();
                    done = true;
                } else {
                    output.println(message);
                }
            } catch (IOException e) {
                client.shutDown();
            }
        }
    }
}