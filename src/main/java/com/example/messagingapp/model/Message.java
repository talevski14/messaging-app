package com.example.messagingapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String body;
    private LocalDateTime timeSent;
    @ManyToOne
    private User sender;

    public Message(String body, LocalDateTime timeSent, User sender) {
        this.body = body;
        this.timeSent = timeSent;
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Message() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeSent() {
        return timeSent.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }
}
