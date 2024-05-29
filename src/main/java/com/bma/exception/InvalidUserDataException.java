package com.bma.exception;

public class InvalidUserDataException extends Exception {

    public InvalidUserDataException(){}

    public InvalidUserDataException(String message){
        super(message);
    }
}
