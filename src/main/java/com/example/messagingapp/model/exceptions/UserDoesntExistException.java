package com.example.messagingapp.model.exceptions;

public class UserDoesntExistException extends RuntimeException{
    public UserDoesntExistException() {
        super("This user doesn't exist.");
    }
}
