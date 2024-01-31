package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.repository.UserRepository;
import com.example.messagingapp.service.UserService;
import org.springframework.stereotype.Service;

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
}
