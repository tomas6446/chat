package com.chat.backend.server;

import com.chat.backend.clientHandler.ClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
public class Server {
    public static List<ClientHandler> activeClients = new ArrayList<>();

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for clients ...");

            do {
                try {
                    socket = serverSocket.accept();
                    System.out.println("A new client is connected: " + socket);

                    inputStream = new DataInputStream(socket.getInputStream());
                    outputStream = new DataOutputStream(socket.getOutputStream());

                    System.out.println("Assigning new thread for this client");

                    ClientHandler clientHandler = new ClientHandler("client " + activeClients.size(), socket, inputStream, outputStream);
                    System.out.println("Adding this client to active client list");
                    activeClients.add(clientHandler);

                    Thread thread = new Thread(clientHandler);
                    thread.start();
                } catch (Exception e) {
                    socket.close();
                    throw new RuntimeException(e);
                }
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server(5000);
    }
}
