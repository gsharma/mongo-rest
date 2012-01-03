package com.mulesoft.mongo.security;

// Go implement your fancy-shmancy algorithms.. AES, etc
public interface CryptDe {
    public boolean validate(String user, String password);
}
