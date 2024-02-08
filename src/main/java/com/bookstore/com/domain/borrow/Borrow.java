package com.bookstore.com.domain.borrow;

import com.bookstore.com.domain.book.Book;
import com.bookstore.com.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow")
//@Cacheable
@NamedEntityGraph(name = "books-students" , attributeNodes ={
@NamedAttributeNode("students") ,  @NamedAttributeNode("books")})
public class Borrow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id", nullable = false)
    private Long borrow_id;

    @Column(name = "date_borrow")
    private LocalDate date_borrow;

    @Column(name = "date_return")
    private LocalDate date_return;

    @Column(name = "overdue")
    private boolean overdue;

    @Column(name = "fines")
    private double fines;

//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id" )
    @JsonBackReference
    private Book books;

//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "std_id")
    private Student students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return Objects.equals(borrow_id, borrow.borrow_id) && Objects.equals(date_borrow, borrow.date_borrow) && Objects.equals(date_return, borrow.date_return);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrow_id, date_borrow, date_return);
    }





    @Override
    public String toString() {
        return "Borrow{" +
                "borrow_id=" + borrow_id +
                ", date_borrow=" + date_borrow +
                ", date_return=" + date_return +
                ", overdue=" + overdue +
                ", fines=" + fines +
                ", student=" + students.getFirstName()  +
                ", book =" + books.getTitle() +
                '}';
    }



}