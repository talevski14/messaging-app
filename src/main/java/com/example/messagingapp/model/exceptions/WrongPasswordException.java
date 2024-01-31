package com.example.messagingapp.model.exceptions;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Please write the correct password.");
    }
}
