package com.chat;

import com.chat.server.Server;

/**
 * @author Tomas Kozakas
 */
public class ServerApplication {
    public static void main(String[] args) {
        new Server().run();
    }
}
