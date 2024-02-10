package com.example.messagingapp.model.exceptions;

public class UserNotInvitedException extends RuntimeException {
    public UserNotInvitedException() {
        super("Selected user is not invited to the group chat.");
    }
}
