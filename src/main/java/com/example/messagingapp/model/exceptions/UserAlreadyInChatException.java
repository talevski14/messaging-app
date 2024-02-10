package com.example.messagingapp.model.exceptions;

public class UserAlreadyInChatException extends RuntimeException{
    public UserAlreadyInChatException() {
        super("Selected user is already in chat");
    }
}
