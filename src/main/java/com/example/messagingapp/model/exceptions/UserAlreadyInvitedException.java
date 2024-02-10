package com.example.messagingapp.model.exceptions;

public class UserAlreadyInvitedException extends RuntimeException{
    public UserAlreadyInvitedException() {
        super("Selected user is already invited.");
    }
}
