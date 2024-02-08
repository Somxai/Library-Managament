package com.bookstore.com.dao;


import com.bookstore.com.domain.student.Student;
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
public interface StudentRepository extends JpaRepository<Student, Long> {

    @NonNull
    @Override
    @EntityGraph(value = "std-borrow", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(@NonNull Pageable pageable);



    @Transactional
    @Query(value = "SELECT s FROM Student s LEFT JOIN FETCH s.borrowStd b WHERE s.id = :id")
    Optional<Student> fetchById(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT s FROM Student s LEFT JOIN FETCH s.borrowStd WHERE s.firstName LIKE %:name% or s.lastName LIKE %:name%")
    Student fetchByName(@Param("name") String name);


}



