package com.bookstore.com.serviceLevelTest;


import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.BookRepository;
import com.bookstore.com.domain.author.Author;
import com.bookstore.com.domain.book.Book;
import com.bookstore.com.dto.bookDto.BookDto;
import com.bookstore.com.services.authorService.AuthorService;
import com.bookstore.com.services.bookService.BookService;
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
public class BookServTest {

    private static final Logger logger = LoggerFactory.getLogger(BookServTest.class);
    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMappers modelMapper;
    @Mock
    AuthorService authorService;


    @InjectMocks
    private BookService bookService;


    //Mock Object
    List<Book> bookList;
    private Book book;


    private BookDto bookDto;
    private Author author;

    @BeforeEach
    public void setUp(){

        //Create Author
        author = new Author("Somxai","SSM","Laos PDR");


        //Create BookDto
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("The Usual Suspect");
        bookDto.setCategory("Crime");
        bookDto.setPrice(7800.55);
        bookDto.setAuthorName("Somxai");

        //Create Book
        book = new Book();
                book.setId(1L);
                book.setTitle("The Usual Suspect");
                book.setCategory("Crime");
                book.setPrice(7800.55);


        //Create list of bookDto
        bookList = new ArrayList<>();
        bookList.add(new Book(1L, "The Usual Suspect","Crime",7800.50,author));
        bookList.add(new Book(2L, "The Shutter Island","Crime",9000.50,author));
        bookList.add(new Book(3L, "The Shutter Island","Crime",9000.50,author));


    }


    @Test
    void isSaveBookValid(){

        //Arrange
        Mockito.when(authorService.fetchByName("Somxai")).thenReturn(author);
        Mockito.when(modelMapper.DtoToBook(bookDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        //Act
        Book bookAdd = bookService.createBook(bookDto);

        logger.info("book added: " + bookAdd);
        //assert
        Assertions.assertNotNull(bookAdd);
        Assertions.assertNotNull(bookAdd, bookAdd.getTitle());
    }

    @Test
    void isFetchAllValid(){

        //Arrange
        Pageable pageable = PageRequest.of(0,3,Sort.by("title").ascending());
        Page<Book> expectedPage = new PageImpl<>(bookList, pageable, bookList.size());
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(expectedPage);

        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(new BookDto(1L, "The Usual Suspect","Crime",7800.50,"Somxai"));
        bookDtos.add(new BookDto(2L, "The Shutter Island","Crime",9000.50,"Somxai"));
        bookDtos.add(new BookDto(3L, "The Shutter Island","Crime",9000.50,"Somxai"));

        Mockito.when(modelMapper.bookToDTO(bookList.get(0))).thenReturn(bookDtos.get(0));
        Mockito.when(modelMapper.bookToDTO(bookList.get(1))).thenReturn(bookDtos.get(1));
        Mockito.when(modelMapper.bookToDTO(bookList.get(2))).thenReturn(bookDtos.get(2));

        //Act
        List<BookDto> bookDtos1 = bookService.fetchAll(0,3,"title");
        System.out.println("Book dtos" + bookDtos1.size());

        //Assertion
        Assertions.assertNotNull(bookDtos1);
        Assertions.assertEquals(3,bookDtos1.size());
    }

    @Test
    void isFetchByAuthorValid(){

        // Arrange
        List<Book> books = bookList;
        Mockito.when(bookRepository.fetchByAuthor("Somxai")).thenReturn(books);
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(new BookDto(1L, "The Usual Suspect","Crime",7800.50,"Somxai"));
        bookDtos.add(new BookDto(2L, "The Shutter Island","Crime",9000.50,"Somxai"));
        bookDtos.add(new BookDto(3L, "The Shutter Island","Crime",9000.50,"Somxai"));

        Mockito.when(modelMapper.bookToDTO(bookList.get(0))).thenReturn(bookDtos.get(0));
        Mockito.when(modelMapper.bookToDTO(bookList.get(1))).thenReturn(bookDtos.get(1));
        Mockito.when(modelMapper.bookToDTO(bookList.get(2))).thenReturn(bookDtos.get(2));

        // Act
        List<BookDto> bookDTO = bookService.fetchByAuthor("Somxai");

        // Debugging
        logger.info("bookDTO from service Test : " + bookDTO);


        // Assert
        Assertions.assertNotNull(bookDTO);
    }




    @Test
    void isFetchByIdValid(){

            Author authorSetup = new Author(1L,"Oda","JoyBoy","Japan");

            Book bookWithAuthor = new Book(1L,"Onepiece","Advanture",500.50, authorSetup);

          // Arrange
            Mockito.when(bookRepository.fetchById(1L)).thenReturn(Optional.of(bookWithAuthor));

            BookDto bookDto = new BookDto();
            bookDto.setId(bookWithAuthor.getId());
            bookDto.setTitle(bookWithAuthor.getTitle());
            bookDto.setCategory(bookWithAuthor.getCategory());
            bookDto.setPrice(bookWithAuthor.getPrice());
            bookDto.setAuthorName(bookWithAuthor.getAuthor().getFirstName() + " " + bookWithAuthor.getAuthor().getLastName());


            Mockito.when(modelMapper.bookToDTO(bookWithAuthor)).thenReturn(bookDto);
            // Act
            BookDto bookDTO = bookService.fetchById(1L);

            // Debugging
            logger.info("book: " + bookWithAuthor);
            logger.info("bookDTO from service Test : " + bookDTO);
            logger.info("BooDto from mapping: " + bookDto);

            // Assert
            Assertions.assertNotNull(bookDTO);
        }


    @Test
    void isFetchByTitleValid(){
        Book bookSetup = new Book(1L,"Onepiece","Advanture",500.50);
        //Arrange
        Mockito.when(bookRepository.fetchByTitle("Onepiece")).thenReturn(bookSetup);

        BookDto bookDto = new BookDto();
        bookDto.setId(bookSetup.getId());
        bookDto.setTitle(bookSetup.getTitle());
        bookDto.setCategory(bookSetup.getCategory());
        bookDto.setPrice(bookSetup.getPrice());

        Mockito.when(modelMapper.bookToDTO(bookSetup)).thenReturn(bookDto);

        logger.info("start bookservice fetchByTitle....");
        //Act
        BookDto bookDTO = bookService.fetchByTitle("Onepiece");
        logger.info("bookDTO : " + bookDTO);

        //Assert
        Assertions.assertNotNull(bookDTO);

    }

    @Test
    void isRemoveValid(){
        Author authorSetup = new Author(1L,"Oda","JoyBoy","Japan");

        Book bookWithAuthor = new Book(1L,"Onepiece","Advanture",500.50, authorSetup);
        Optional<Book> optionalBook = Optional.of(bookWithAuthor);

        //Arrange
        Mockito.when(bookRepository.fetchById(1L)).thenReturn(optionalBook);

        Mockito.doNothing().when(bookRepository).delete(bookWithAuthor);
        logger.info("check book is ok? :" + bookWithAuthor.getId());

        boolean delBook = bookService.removeBook(bookWithAuthor);
        logger.info("is true or false: " + delBook);
        Assertions.assertTrue(delBook);
        Mockito.verify(bookRepository).delete(bookWithAuthor);

    }


}
