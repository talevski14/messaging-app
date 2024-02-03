package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.repository.UserRepository;
import com.example.messagingapp.service.UserService;
import com.sun.jdi.InvalidStackFrameException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String fullName, String username, String password, String repeatPassword) {
        if(fullName == null || username == null || password == null || repeatPassword == null || fullName.isEmpty() || username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        if(!password.equals(repeatPassword)) {
            throw new PasswordsDontMatchException();
        }

        if(userRepository.existsById(username)) {
            throw new UserExistsException();
        }

        User user = new User(fullName, username, password);

        return userRepository.save(user);
    }

    @Override
    public User logIn(String username, String password) {
        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        User user = userRepository.findById(username).orElseThrow(UserDoesntExistException::new);

        if(!Objects.equals(user.getPassword(), password)) {
            throw new WrongPasswordException();
        }

        return user;
    }

    @Override
    public User findCurrentUser(String username) {
        if(username==null || username.isEmpty()) {
            throw new UserNotLoggedInException();
        }
        return userRepository.findById(username).orElseThrow(UserDoesntExistException::new);
    }

    @Override
    public List<User> findAllUsersById(String username) {
        if(username==null || username.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findAllByUsernameContainsIgnoreCase(username);
    }

    @Override
    public User findUser(String username) {
        if(username==null || username.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findById(username).orElseThrow(UserDoesntExistException::new);
    }

    @Override
    public void sendFriendRequest(User sender, User receiver) {
        if(sender.getFriendRequests().contains(receiver)) {
            throw new FriendRequestSenderException();
        }

        if(receiver.getFriendRequests().contains(sender)) {
            throw new FriendRequestReceiverException();
        }

        if(sender.getFriends().contains(receiver)) {
            throw new AlreadyFriendsException();
        }

        receiver.getFriendRequests().add(sender);
        userRepository.save(receiver);
    }

    @Override
    public void declineRequest(User currentUser, String username) {
        User user = findUser(username);
        currentUser.getFriendRequests().remove(user);
        userRepository.save(currentUser);
    }

    @Override
    public void acceptRequest(User currentUser, String username) {
        User user = findUser(username);
        currentUser.getFriendRequests().remove(user);
        currentUser.getFriends().add(user);
        user.getFriends().add(currentUser);
        userRepository.save(currentUser);
        userRepository.save(user);
    }
}
