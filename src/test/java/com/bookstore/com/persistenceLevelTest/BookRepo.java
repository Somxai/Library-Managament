package com.bookstore.com.persistenceLevelTest;


import com.bookstore.com.dao.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepo {

    private static final Logger loggTest = LoggerFactory.getLogger(BookRepo.class);

    @Autowired
    BookRepository bookRepository;






}
