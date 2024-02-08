package com.bookstore.com.dto.studentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {

    private Long std_id;


    private String firstName;


    private String lastName;


    private String gender;


    private int age;


    private String email;


    private List<String> borrowedBook;


    @Override
    public String toString() {
        return "StudentDto{" +
                "std_id=" + std_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public void borrowedBooks(List<String> books){
        this.borrowedBook = books;

    }



}


