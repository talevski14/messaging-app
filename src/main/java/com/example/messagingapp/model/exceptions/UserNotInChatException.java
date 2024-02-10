package com.example.messagingapp.model.exceptions;

public class UserNotInChatException extends RuntimeException {
    public UserNotInChatException() {
        super("You can't view this chat.");
    }
}
