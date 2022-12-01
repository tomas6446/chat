package com.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tomas Kozakas
 */
public class Server implements Runnable {

    private final List<ConnectionHandler> connections = new ArrayList<>();
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean done = false;

    public static void main(String[] args) {
        new Server().run();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            executorService = Executors.newCachedThreadPool();
            System.out.println("Server starting...\nWaiting for clients");
            while (!done) {
                Socket socket = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(socket);
                connections.add(connectionHandler);
                executorService.execute(connectionHandler);
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public void broadCastEveryone(String message) {
        connections.forEach(connection -> {
            if (message != null) {
                connection.sendMessage(message);
            }
        });
    }

    public boolean broadCastTo(String recipient, String message) {
        for (ConnectionHandler connection : connections) {
            if (message != null && Objects.equals(connection.username, recipient)) {
                connection.sendMessage(message);
                return true;
            }
        }
        return false;
    }

    public boolean broadCastChatRoom(String message) {
        for (ConnectionHandler connection : connections) {
            if (message != null && connection.connectedToChatRoom) {
                connection.sendMessage(message);
                return true;
            }
        }
        return false;
    }


    public void shutDown() {
        if (!serverSocket.isClosed()) {
            try {
                done = true;
                executorService.shutdown();
                serverSocket.close();
            } catch (IOException ignored) {
            }
            for (ConnectionHandler connection : connections) {
                connection.shutDown();
            }
        }
    }

    class ConnectionHandler implements Runnable {
        private final Socket socket;
        private BufferedReader input;
        private PrintWriter output;
        private String username;
        private boolean connectedToChatRoom = true;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output.println("Please enter an username: ");
                username = input.readLine();
                System.out.println(username + " connected!");

                menu();
            } catch (Exception e) {
                shutDown();
            }
        }

        private void menu() throws IOException {
            output.println("1. Private message\n2. Chat room\nChoice:");
            String message;
            switch (Integer.parseInt(input.readLine())) {
                case 1 -> {
                    output.println("Recipient:");
                    String recipient = input.readLine();
                    while ((message = input.readLine()) != null) {
                        output.println(username + ": ");
                        if (message.startsWith("/quit")) {
                            shutDown();
                        } else if (message.startsWith("/return")) {
                            menu();
                        } else {
                            if (!broadCastTo(recipient, username + ": " + message)) {
                                System.out.println("The " + recipient + " is offline");
                                menu();
                            }
                            System.out.println(username + ": " + message);
                        }
                    }
                }
                case 2 -> {
                    broadCastChatRoom(username + " joined the chat!");
                    connectedToChatRoom = true;
                    while ((message = input.readLine()) != null) {
                        output.print(username + ": ");
                        if (message.startsWith("/quit")) {
                            broadCastChatRoom(username + " left the chat");
                            shutDown();
                        } else if (message.startsWith("/return")) {
                            connectedToChatRoom = false;
                            menu();
                        } else {
                            if (!broadCastChatRoom(username + ": " + message)) {
                                System.out.println("Unable to send the message");
                                menu();
                            }
                            System.out.println(username + ": " + message);
                        }
                    }
                }
                default -> menu();
            }
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        public void shutDown() {
            try {
                input.close();
                output.close();
            } catch (IOException ignored) {
            }
            if (!serverSocket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
