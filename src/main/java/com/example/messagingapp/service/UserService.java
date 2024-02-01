package com.example.messagingapp.service;

import com.example.messagingapp.model.User;

import java.util.List;

public interface UserService {
    public User createUser(String fullName, String username, String password, String repeatPassword);
    public User logIn(String username, String password);
    public User findCurrentUser(String username);
    public List<User> findAllUsersById(String username, User user);
    public User findUser(String username);
    public void sendFriendRequest(User sender, User receiver);

    void declineRequest(User currentUser, String username);

    void acceptRequest(User currentUser, String username);
}
