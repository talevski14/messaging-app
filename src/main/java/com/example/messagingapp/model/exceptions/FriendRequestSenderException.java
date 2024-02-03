package com.example.messagingapp.model.exceptions;

public class FriendRequestSenderException extends RuntimeException{
    public FriendRequestSenderException() {
        super("They have already sent you a request.");
    }
}
