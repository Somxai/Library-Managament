package com.bookstore.com.exception.authorException;

public class AuthorAlreadyExistException extends RuntimeException{

    String message;

    public AuthorAlreadyExistException() {

    }
    public AuthorAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
