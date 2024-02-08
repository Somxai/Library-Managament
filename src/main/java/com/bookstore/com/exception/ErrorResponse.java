package com.bookstore.com.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {


    private String message;
    private String detail;


    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
