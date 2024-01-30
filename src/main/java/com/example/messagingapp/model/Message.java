package com.example.messagingapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @ManyToOne
    private Group group;
    private String body;
    private LocalDateTime timeSent;

    public Message(User sender, User receiver, String body, LocalDateTime timeSent) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.timeSent = timeSent;
    }

    public Message(User sender, Group group, String body, LocalDateTime timeSent) {
        this.sender = sender;
        this.group = group;
        this.body = body;
        this.timeSent = timeSent;
    }

    public Message() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }
}
