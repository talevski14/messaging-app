package com.example.messagingapp.model.exceptions;

public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException() {
        super("Please fill all of the fields.");
    }
}
