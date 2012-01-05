package com.github.mongorest.exception;

public class AuthorizationException extends Exception {
    private static final long serialVersionUID = 2493149675329603996L;

    public AuthorizationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
