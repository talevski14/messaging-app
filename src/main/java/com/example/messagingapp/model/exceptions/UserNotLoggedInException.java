package com.example.messagingapp.model.exceptions;

public class UserNotLoggedInException extends RuntimeException{
    public UserNotLoggedInException() {
        super("Please log in to continue to this page.");
    }
}
