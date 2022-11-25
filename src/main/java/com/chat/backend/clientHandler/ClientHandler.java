package com.chat.backend.clientHandler;

import com.chat.backend.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Tomas Kozakas
 */
public class ClientHandler implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private final String name;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Socket socket;
    boolean isLoggedIn;

    public ClientHandler(String name, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.name = name;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {
        String received;
        do {
            try {
                received = inputStream.readUTF();
                System.out.println(received);

                if (received.equals("exit")) {
                    System.out.println("Client " + socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    socket.close();

                    System.out.println("Connection closed");
                    isLoggedIn = false;
                    break;
                }

                StringTokenizer stringTokenizer = new StringTokenizer(received, "#");
                String msgToSend = stringTokenizer.nextToken();
                String recipient = stringTokenizer.nextToken();

                // TODO: optimise speed of finding the client by implementing HashMap
                Server.activeClients.forEach(client -> {
                    if(client.name.equals(recipient) && client.isLoggedIn) {
                        try {
                            client.outputStream.writeUTF(name + " : " + msgToSend);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (true);

        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
