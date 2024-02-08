package com.bookstore.com.dto.bookDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    private Long id;
    private String title;
    private String category;
    private Double price;
    private String authorName;


    public BookDto(String title, String category, Double price, String authorName) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", authorName='" + authorName + '\'' +
                '}';
    }

    public String setAuthorName(String authorName) {
        this.authorName = authorName;
        return authorName;
    }
}
