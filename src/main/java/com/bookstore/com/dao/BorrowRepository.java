package com.bookstore.com.dao;


import com.bookstore.com.domain.borrow.Borrow;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Override
    @Transactional
    @NonNull
    @EntityGraph(value = "books-students", type = EntityGraph.EntityGraphType.LOAD)
    Page<Borrow> findAll(@NonNull Pageable pageable);

    @Transactional
    @Query(value = "SELECT b FROM Borrow b LEFT JOIN FETCH b.books " +
            "LEFT JOIN FETCH b.students a WHERE b.id =:id")
    Optional<Borrow> fetchById(@Param("id") Long id);


}