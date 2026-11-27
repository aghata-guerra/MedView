package com.guerra.medview.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid e-mail or password");
    }
}
