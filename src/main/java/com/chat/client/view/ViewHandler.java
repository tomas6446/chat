package com.chat.client.view;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchConnectionWindow() throws IOException;

    void launchMainWindow() throws IOException;

    void launchChatWindow() throws IOException;
}
