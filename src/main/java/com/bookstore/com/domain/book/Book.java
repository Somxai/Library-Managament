package com.bookstore.com.domain.book;


import com.bookstore.com.domain.author.Author;
import com.bookstore.com.domain.borrow.Borrow;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "book")
@Cacheable
@NamedEntityGraph(name = "book-author" , attributeNodes = @NamedAttributeNode("author"))
@NamedEntityGraph(name = "book-borrow" , attributeNodes = @NamedAttributeNode("borrowBk"))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Double price;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Borrow> borrowBk = new ArrayList<>();


    public Book(Long id ,String title, String category, Double price) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public Book(Long id, String title, String category, Double price, Author author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.author = author;
    }


    public void addBorrow(Borrow borrow){
        borrowBk.add(borrow);
        borrow.setBooks(this);


    }
    public void removeBorrow(Borrow borrow){
        borrowBk.remove(borrow);
        borrow.setBooks(null);

    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
