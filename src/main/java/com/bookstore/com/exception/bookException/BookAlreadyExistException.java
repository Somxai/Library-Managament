package com.bookstore.com.exception.bookException;

public class BookAlreadyExistException extends RuntimeException{

    String message;

    public BookAlreadyExistException() {

    }
    public BookAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
