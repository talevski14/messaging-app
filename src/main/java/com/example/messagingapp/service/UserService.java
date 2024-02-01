package com.example.messagingapp.service;

import com.example.messagingapp.model.User;

import java.util.List;

public interface UserService {
    public User createUser(String fullName, String username, String password, String repeatPassword);
    public User logIn(String username, String password);
    public User findUser(String username);
    public List<User> findAllUsersById(String username);
}
