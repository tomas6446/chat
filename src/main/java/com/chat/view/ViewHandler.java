package com.chat.view;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchLoginWindow() throws IOException;

    void launchMainWindow() throws IOException;

    void launchChatWindow() throws IOException;
}
