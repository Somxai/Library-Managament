package com.bookstore.com.domain.author;


import com.bookstore.com.domain.book.Book;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "author")
@Cacheable()
@NamedEntityGraph(name = "author-book" , attributeNodes = @NamedAttributeNode("books"))
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "nation")
    private String nation;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName, String nation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nation = nation;
    }

    public Author(Long authorId, String firstName, String lastName, String nation) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nation = nation;
    }

    public void addBook(Book book){
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book){
        this.books.remove(book);
        book.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(nation, author.nation) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, firstName, lastName, nation, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}
