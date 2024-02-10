package com.example.messagingapp.model.exceptions;

public class NotAGroupException extends RuntimeException {
    public NotAGroupException() {
        super("This is not a group chat.");
    }
}
