package com.mulesoft.mongo.exception;

public class AuthenticationException extends Exception {
    private static final long serialVersionUID = -4422309598889532630L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
