package com.example.messagingapp.model.exceptions;

public class PasswordsDontMatchException extends RuntimeException{
    public PasswordsDontMatchException() {
        super("Passwords don't match.");
    }
}
