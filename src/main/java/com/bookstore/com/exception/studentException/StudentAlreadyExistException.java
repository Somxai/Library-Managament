package com.bookstore.com.exception.studentException;

public class StudentAlreadyExistException extends RuntimeException{

    String message;

    public StudentAlreadyExistException() {

    }
    public StudentAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
