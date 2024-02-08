package com.bookstore.com.exception.authorException;

public class NoSuchAuthorExistException extends RuntimeException {

    String message;

    public NoSuchAuthorExistException(){

    }

    public NoSuchAuthorExistException(String message) {
        super(message);
        this.message = message;

    }
}
