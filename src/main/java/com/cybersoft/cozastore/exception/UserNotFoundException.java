package com.cybersoft.cozastore.exception;

public class UserNotFoundException extends RuntimeException{
    private String message;
    public UserNotFoundException(String message){

        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
