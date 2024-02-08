package com.bookstore.com.exception.borrowException;

public class BorrowAlreadyExistException extends RuntimeException{

    String message;

    public BorrowAlreadyExistException() {

    }
    public BorrowAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
