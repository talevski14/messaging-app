package com.example.messagingapp.model;

public class ClientMessage {
    private String username;
    private String body;
    private String time;

    public ClientMessage(String username, String body, String time) {
        this.username = username;
        this.body = body;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
