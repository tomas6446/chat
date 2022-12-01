package com.chat.server;

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
    private boolean done;

    public static void main(String[] args) {
        new Client().run();
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            InputHandler inputHandler = new InputHandler();
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
        done = true;
        try {
            input.close();
            output.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Server not started");
        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (!done) {
                try {
                    String message = input.readLine();
                    if (message.equals("/quit")) {
                        output.println(message);
                        input.close();
                        shutDown();
                    } else {
                        output.println(message);
                    }
                } catch (IOException e) {
                    shutDown();
                }
            }
        }
    }
}
