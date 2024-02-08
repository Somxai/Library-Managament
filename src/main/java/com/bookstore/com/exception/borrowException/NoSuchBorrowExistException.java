package com.bookstore.com.exception.borrowException;

public class NoSuchBorrowExistException extends RuntimeException {

    String message;

    public NoSuchBorrowExistException(){

    }

    public NoSuchBorrowExistException(String message) {
        super(message);
        this.message = message;

    }
}
