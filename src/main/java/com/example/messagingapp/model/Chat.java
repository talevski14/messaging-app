package com.example.messagingapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    private Boolean isGroup;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private User creator;
    private String name;
    @ManyToMany
    private List<User> members;
    @OneToMany
    private List<Message> messages;
    @ManyToMany
    private List<User> invitedMembers;

    public Chat(List<User> members) {
        this.isGroup = false;
        this.creator = null;
        this.name = null;
        this.members = members;
        this.messages = new ArrayList<>();
        this.invitedMembers = null;
    }

    public Chat(User creator, String name) {
        this.isGroup = true;
        this.creator = creator;
        this.name = name;
        this.members = new ArrayList<>();
        members.add(creator);
        this.messages = new ArrayList<>();
        this.invitedMembers = new ArrayList<>();
    }

    public Chat() {
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

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<User> getInvitedMembers() {
        return invitedMembers;
    }

    public void setInvitedMembers(List<User> invitedMembers) {
        this.invitedMembers = invitedMembers;
    }
}
