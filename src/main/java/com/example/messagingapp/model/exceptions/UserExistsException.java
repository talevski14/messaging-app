package com.example.messagingapp.model.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super("This username is taken.");
    }
}
