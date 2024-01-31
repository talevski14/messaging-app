package com.example.messagingapp.service;

import com.example.messagingapp.model.User;

public interface UserService {
    public User createUser(String fullName, String username, String password, String repeatPassword);
    public User logIn(String username, String password);
}
