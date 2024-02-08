package com.bookstore.com.persistenceLevelTest;

import com.bookstore.com.dao.AuthorRepository;
import com.bookstore.com.domain.author.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorRepo {


    private static final Logger loggTest = LoggerFactory.getLogger(AuthorRepo.class);
    @Autowired
    AuthorRepository authorRepository;



    @Test
    void isFindAuthorByNameValid(){

        Author author = authorRepository.fetchByName("Mr Framinggo");

        Assertions.assertNotNull(author);
        loggTest.info("author object :" + author.toString());
        loggTest.info("book come along :" + author.getBooks());

    }




}
