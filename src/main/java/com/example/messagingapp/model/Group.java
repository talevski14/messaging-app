package com.example.messagingapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userGroup")
public class Group {
    private String name;
    @ManyToOne
    private User creator;
    @ManyToMany
    private List<User> members;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToMany
    private List<Message> messages;

    public Group(String name, User creator) {
        this.name = name;
        this.creator = creator;
        this.members = new ArrayList<>();
        this.members.add(creator);
        this.messages = new ArrayList<>();
    }

    public Group() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
