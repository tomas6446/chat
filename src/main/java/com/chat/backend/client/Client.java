package com.chat.backend.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Tomas Kozakas
 */
public class Client {
    public Client(String address, int port) {
        try {
            Scanner scanner = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName(address);
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + ":" + port);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            Thread sendMessage = new Thread(() -> {
                do {
                    // read the message to deliver
                    String msg = scanner.nextLine();
                    try {
                        // write it in output stream
                        outputStream.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (true);
            });

            Thread readMessage = new Thread(() -> {
                do{
                    try {
                        // read the message sent to this client
                        String msg = inputStream.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (true);
            });

            sendMessage.start();
            readMessage.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 5000);
    }
}
