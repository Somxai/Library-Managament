package com.bookstore.com.serviceLevelTest;

import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.AuthorRepository;
import com.bookstore.com.domain.author.Author;
import com.bookstore.com.dto.authorDto.AuthorDto;
import com.bookstore.com.services.authorService.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    Logger logger = LoggerFactory.getLogger(AuthorService.class);
    @Mock
    AuthorRepository authorRepository;
    @Mock
    ModelMappers modelMappers;
    @InjectMocks
    AuthorService authorService;
    List<AuthorDto> authorDtoList;
    List<Author> authorList;

    @BeforeEach
    public void init() {

        authorList = new ArrayList<>();
        authorList.add(new Author(1L, "Somxai", "SSM", "Laos"));
        authorList.add(new Author(2L, "Somxai", "SSM", "Laos"));
        authorList.add(new Author(3L, "Somxai", "SSM", "Laos"));

        authorDtoList = new ArrayList<>();
        authorDtoList.add(new AuthorDto(1L, "Somxai", "SSM", "Laos"));
        authorDtoList.add(new AuthorDto(2L, "Somxai", "SSM", "Laos"));
        authorDtoList.add(new AuthorDto(3L, "Somxai", "SSM", "Laos"));

    }

    @Test
    public void isFetchAllValid() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(1L, "Somxai", "SSM", "Laos"));
        authorList.add(new Author(2L, "Somxai", "SSM", "Laos"));
        authorList.add(new Author(3L, "Somxai", "SSM", "Laos"));

        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorDtoList.add(new AuthorDto(1L, "Somxai", "SSM", "Laos"));
        authorDtoList.add(new AuthorDto(2L, "Somxai", "SSM", "Laos"));
        authorDtoList.add(new AuthorDto(3L, "Somxai", "SSM", "Laos"));


        Pageable pageable = PageRequest.of(0, 2, Sort.by("nation"));
        Page<Author> authors = new PageImpl<>(authorList);
        Mockito.when(authorRepository.findAll(pageable)).thenReturn(authors);

        Mockito.when(modelMappers.authorToDto(authorList.get(0))).thenReturn(authorDtoList.get(0));
        Mockito.when(modelMappers.authorToDto(authorList.get(1))).thenReturn(authorDtoList.get(1));
        Mockito.when(modelMappers.authorToDto(authorList.get(2))).thenReturn(authorDtoList.get(2));


        List<AuthorDto> authorDtos = authorService.fetchAll(0, 2, "nation");

        Assertions.assertNotNull(authorDtos);

    }

    @Test
    void isFetchAuthorByIdValid() {
        Author author = new Author(1L, "Jack", "SSM", "Mars");
        Mockito.when(authorRepository.fetchById(1L)).thenReturn(Optional.of(author));

        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorId(author.getAuthorId());
        authorDto.setFirstName(author.getFirstName());
        authorDto.setLastName(author.getLastName());
        authorDto.setNation(author.getNation());
        Mockito.when(modelMappers.authorToDto(author)).thenReturn(authorDto);
        AuthorDto authorDtoResult = authorService.fetchById(1L);
        Assertions.assertNotNull(authorDtoResult);
    }

    @Test
    void isFetchByNameValid() {
        Author author = new Author(1L, "Jack", "SSM", "Mars");
        String name = "Jack";

        Mockito.when(authorRepository.fetchByName(name)).thenReturn(author);

        Author authors = authorService.fetchByName(name);

        Assertions.assertNotNull(authors);

    }

    @Test
    void isCreteAuthorValid() {
        AuthorDto authorDto = new AuthorDto(1L, "Jack", "SSM", "Mars");
        Author author = new Author(1L, "Jack", "SSM", "Mars");


        Mockito.when(modelMappers.DtoToAuthor(authorDto)).thenReturn(author);
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        //Act
        Author createdAuthor = authorService.createAuthor(authorDto);
        logger.info("author after create" + createdAuthor.toString());

        Assertions.assertNotNull(createdAuthor);


    }
}

//    @Test
//    void isRemoveAuthorValid(){
//    AuthorDto author = new Author(1L,"Jack","SSM","Mars");
//
//
//    Mockito.when(authorRepository.fetchById(1L)).thenReturn(Optional.of(author));
//    Mockito.doNothing().when(authorRepository).delete(author);
//
//
//    boolean result = authorService.removeAuthor();
//
//    Assertions.assertTrue(result);
//    Mockito.verify(authorRepository).delete(author);
//
//
//
//    }
//}
