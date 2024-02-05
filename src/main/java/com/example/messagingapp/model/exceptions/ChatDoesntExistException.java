package com.example.messagingapp.model.exceptions;

public class ChatDoesntExistException extends RuntimeException {
    public ChatDoesntExistException() {
        super("The chat you are looking for doesn't exist.");
    }
}
