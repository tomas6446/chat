package com.chat.view;

import com.chat.server.Listener;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow(Listener listener) throws IOException;

    void launchChatWindow(Listener listener) throws IOException;
}
