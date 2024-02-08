package com.bookstore.com.exception;

import com.bookstore.com.exception.authorException.AuthorAlreadyExistException;
import com.bookstore.com.exception.authorException.NoSuchAuthorExistException;
import com.bookstore.com.exception.bookException.BookAlreadyExistException;
import com.bookstore.com.exception.bookException.NoSuchBookExistException;
import com.bookstore.com.exception.borrowException.BorrowAlreadyExistException;
import com.bookstore.com.exception.borrowException.NoSuchBorrowExistException;
import com.bookstore.com.exception.studentException.NoSuchStudentExistException;
import com.bookstore.com.exception.studentException.StudentAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> somethingWentWrong(Exception ex, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NoSuchAuthorExistException.class)
    public ResponseEntity<ErrorResponse> authorNotFoundException(NoSuchAuthorExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(AuthorAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> authorAlreadyExistException(AuthorAlreadyExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.CONFLICT);

    }


    @ExceptionHandler(NoSuchStudentExistException.class)
    public ResponseEntity<ErrorResponse> studentNotFoundException(NoSuchStudentExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(StudentAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> studentAlreadyExistException(StudentAlreadyExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.CONFLICT);

    }



    @ExceptionHandler(NoSuchBookExistException.class)
    public ResponseEntity<ErrorResponse> bookNotFoundException(NoSuchBookExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> bookAlreadyExistException(BookAlreadyExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.CONFLICT);

    }


    @ExceptionHandler(NoSuchBorrowExistException.class)
    public ResponseEntity<ErrorResponse> borrowNotFoundException(NoSuchBorrowExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BorrowAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> borrowAlreadyExistException(BorrowAlreadyExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"Error With: " +ex.toString());

        return new ResponseEntity<ErrorResponse>(errorResponse,new HttpHeaders(), HttpStatus.CONFLICT);

    }



}
