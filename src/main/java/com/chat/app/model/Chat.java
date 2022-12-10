package com.chat.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas
 */
@Getter
@Setter
public abstract class Chat {
    protected List<String> messages = new ArrayList<>();
    protected String name;
}
