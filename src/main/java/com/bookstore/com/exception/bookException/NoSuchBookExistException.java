package com.bookstore.com.exception.bookException;

public class NoSuchBookExistException extends RuntimeException {

    String message;

    public NoSuchBookExistException(){

    }

    public NoSuchBookExistException(String message) {
        super(message);
        this.message = message;

    }
}
