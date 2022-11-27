package com.chat.client.model;

import java.io.Serializable;

/**
 * @author Tomas Kozakas
 */
public class User implements Serializable {
    private String username;
    private String address;
    private Integer port;

    public User(String username, String address, Integer port) {
        this.username = username;
        this.address = address;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", port=" + port +
                '}';
    }
}
