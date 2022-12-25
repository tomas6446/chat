package com.chat.server;

import lombok.Getter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
public class ServerHandler extends Thread {
    private final List<ServerThread> serverThreads = new ArrayList<>();

    public void addSocket(Socket socket) {
        ServerThread serverThread = new ServerThread(socket, this);
        serverThreads.add(serverThread);
        serverThread.start();
    }
}
