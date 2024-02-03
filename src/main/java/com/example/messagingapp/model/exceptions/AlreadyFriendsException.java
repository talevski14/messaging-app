package com.example.messagingapp.model.exceptions;

public class AlreadyFriendsException extends RuntimeException {
    public AlreadyFriendsException() {
        super("You two are already friends!");
    }
}
