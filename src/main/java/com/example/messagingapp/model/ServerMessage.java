package com.example.messagingapp.model;

import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class ServerMessage {
    private String body;
    private String sender;

    public ServerMessage(String body, String sender) {
        this.body = body;
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
