package com.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private String message;

}
