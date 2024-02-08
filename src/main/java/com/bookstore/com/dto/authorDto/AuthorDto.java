package com.bookstore.com.dto.authorDto;


import com.bookstore.com.dto.bookDto.BookDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto implements Serializable {


    private Long authorId;


    private String firstName;

    private String lastName;

    private String nation;

    private List<BookDto> books = new ArrayList<>();



    public AuthorDto(Long authorId, String firstName, String lastName, String nation) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nation = nation;
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}
