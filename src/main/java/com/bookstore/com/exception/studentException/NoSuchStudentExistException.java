package com.bookstore.com.exception.studentException;

public class NoSuchStudentExistException extends RuntimeException {

    String message;

    public NoSuchStudentExistException(){

    }

    public NoSuchStudentExistException(String message) {
        super(message);
        this.message = message;

    }
}
