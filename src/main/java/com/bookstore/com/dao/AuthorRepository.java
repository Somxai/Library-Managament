package com.bookstore.com.dao;


import com.bookstore.com.domain.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @NonNull
    @Override
    @EntityGraph(value = "author-book", type = EntityGraph.EntityGraphType.LOAD)
    public Page<Author> findAll(@NonNull Pageable pageable);

    @Transactional
    @Query(value = "SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE a.firstName = :name OR a.lastName =:name")
    public Author fetchByName(@Param("name") String name);

    @Transactional
    @Query(value = "SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE a.id = :id")
    Optional<Author> fetchById(@Param("id") Long id);

}