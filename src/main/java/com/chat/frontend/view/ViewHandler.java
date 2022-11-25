package com.chat.frontend.view;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public interface ViewHandler {
    void launchAuthWindow() throws IOException;
    void launchMainWindow() throws IOException;
}
