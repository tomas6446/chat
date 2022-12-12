package com.chat.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class Client implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            InputHandler inputHandler = new InputHandler(this, output);
            Thread thread = new Thread(inputHandler);
            thread.start();

            String inMessage;
            while ((inMessage = input.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public void shutDown() {
        try {
            input.close();
            output.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("server did not start");
        }
    }
}
