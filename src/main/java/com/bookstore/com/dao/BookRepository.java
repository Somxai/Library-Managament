package com.bookstore.com.dao;


import com.bookstore.com.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    @NonNull
    @Override
    @Transactional
    @EntityGraph(value = "book-author", type = EntityGraph.EntityGraphType.LOAD)
    Page<Book> findAll(@NonNull Pageable pageable);

    @Transactional
    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.author a WHERE b.id = :id")
    Optional<Book> fetchById(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.author a WHERE b.title = :title")
    Book fetchByTitle(@Param("title") String title);

    @Transactional
    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.author WHERE b.author.firstName LIKE %:auname% OR b.author.lastName LIKE %:auname%")
    List<Book> fetchByAuthor(@Param("auname")String firstName);
}
