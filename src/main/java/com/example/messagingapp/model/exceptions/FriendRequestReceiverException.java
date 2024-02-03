package com.example.messagingapp.model.exceptions;

public class FriendRequestReceiverException extends RuntimeException{
    public FriendRequestReceiverException() {
        super("You have already sent them a request.");
    }
}
