package com.bookstore.com.services.bookService;

import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.BookRepository;
import com.bookstore.com.domain.author.Author;
import com.bookstore.com.domain.book.Book;
import com.bookstore.com.dto.bookDto.BookDto;
import com.bookstore.com.exception.bookException.BookAlreadyExistException;
import com.bookstore.com.exception.bookException.NoSuchBookExistException;
import com.bookstore.com.services.authorService.AuthorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final ModelMappers modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @PersistenceContext
    EntityManager entityManager;

    private final AuthorService authorService;
    @Autowired
    public BookService(ModelMappers modelMapper, BookRepository bookRepository, @Lazy AuthorService authorService) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Transactional
    @Cacheable(value = "book", key = "#title")
    public BookDto fetchByTitle(String title){

        try {
            logger.info("start fetch book by name process");
            Book book = bookRepository.fetchByTitle(title);

            logger.info("book return : " + book);
            BookDto bookDto = modelMapper.bookToDTO(book);

            logger.info("bookMapped :" + bookDto);
            return bookDto;

        }catch (NoSuchBookExistException e){

            logger.info("something went wrong" + e.getMessage());
            throw new NoSuchBookExistException("fetchBookByTitle Not found: " + title);
        }

    }

    @Transactional
//    @Cacheable(value = "book", key = "#title")
    public Book fetchByTitleNormal(String title){

        try {
            logger.info("start fetch book by name process");
            Book book = bookRepository.fetchByTitle(title);
            if (book != null){
                logger.info("book return : " + book);

                return book;

            }
            throw new NoSuchBookExistException("book not exist");
        }catch (NoSuchBookExistException e){

            logger.info("something went wrong" + e.getMessage());
            throw new NoSuchBookExistException("fetchBookByTitle Not found: " + title);
        }

    }



    @Transactional
    @Cacheable(value = "book", key = "#firstName")
    public List<BookDto> fetchByAuthor(String firstName){
        try {
            logger.info("start fetch book by author process");

            List<Book> book = bookRepository.fetchByAuthor(firstName);

            logger.info("Book fetch Using Author" + book);

            List<BookDto> bookDto = book.stream().map(modelMapper::bookToDTO).collect(Collectors.toList());

            logger.info("BookDTO Size : " + bookDto.size());

            return bookDto;

        }catch (NoSuchBookExistException e){

            throw new NoSuchBookExistException("FetchBookByAuthor Not found: " + firstName);
        }
    }

    @Transactional
    @Cacheable(value = "book", key = "#id")
    public BookDto fetchById(Long id) {
        Optional<Book> book = bookRepository.fetchById(id);
        try {
            Book book1 = book.orElseThrow(() -> new NoSuchBookExistException("Book with id : " + id + " is not exist in database"));
            logger.info("Mapping book to bookDTO...");
            BookDto bookDTO = modelMapper.bookToDTO(book1);
            logger.info("Mapped book to bookDTO: " + bookDTO); // Log the bookDTO object

            return bookDTO;
        }catch (NoSuchBookExistException e){
            throw new NoSuchBookExistException("Error!" + e.getMessage());
        }
    }

    @Transactional
//    @Cacheable(value = "book", key = "#id")
    public Book fetchByID(Long id) {

        try {
            Optional<Book> book = bookRepository.fetchById(id);
            return book.orElseThrow(() -> new NoSuchBookExistException("Book with id : " + id + " is not exist in database"));

        }catch (NoSuchBookExistException e){
            throw new NoSuchBookExistException("Error!" + e.getMessage());
        }
    }


    @Cacheable(value = "books")
    public List<BookDto> fetchAll(int pageNo, int pageSize, String sort) {
        try {
            logger.info("excuting fetch All");
            Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sort).descending());
            Page<Book> bookPage = bookRepository.findAll(pageable);
            List<BookDto> bookDtos = bookPage.stream().map(modelMapper::bookToDTO).collect(Collectors.toList());
            for (BookDto bookDto : bookDtos){
                System.out.println("book dto after mapped: " + bookDto);

            }
            return bookDtos ;

        }catch (NoSuchBookExistException e){
            logger.error("" , e);
            throw new NoSuchBookExistException( "no set of book found" + e);
        }
    }


    @Transactional
    // TODO: solve cache put later chain with fetch Author by Name
    public Book createBook(BookDto bookDto){
            Book book = modelMapper.DtoToBook(bookDto);
            Optional<Book> bookCheck = bookRepository.fetchById(bookDto.getId());
            if (bookCheck.isPresent()){
                logger.error("this book with id: " + bookDto.getId() +" already exist");
                throw new BookAlreadyExistException("the book with id: " + bookDto.getId() + " is already exist.");
            }
               Author author = new Author();
               author = authorService.fetchByName(bookDto.getAuthorName());
        logger.info("author check : " + author);
            if (author == null){
                book.setAuthor(null);
            }else{
                book.setAuthor(author);
                logger.info("show book with author added: " + author);
                author.addBook(book);
                logger.info("show author with book added: " + author.getBooks());
            }

                return bookRepository.save(book);
    }

    @Transactional
    public boolean removeBook(Book book){
    try {
        Optional<Book> bookToRemove = bookRepository.fetchById(book.getId());

        logger.info("BookOptional " + bookToRemove.get());
        if (bookToRemove.isEmpty()){
            logger.error("return false cuz book is not present or something went wrong");
            throw new NoSuchBookExistException("there is no book id: " + book.getId());
        }

        logger.info("book id is present....");
        bookToRemove.get().getAuthor().removeBook(bookToRemove.get());
        bookRepository.delete(bookToRemove.get());
        return true;

    }catch (Exception e){
        logger.error("unable to remove");
        return false;
    }
    }

}
