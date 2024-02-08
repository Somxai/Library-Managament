package com.bookstore.com.controller.bookController;

import com.bookstore.com.domain.book.Book;
import com.bookstore.com.dto.bookDto.BookDto;
import com.bookstore.com.services.bookService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {


    @Autowired
    BookService bookService;


    @GetMapping( path = "/fetchBooks")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<BookDto> fetchAllBook(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                                      @RequestParam(value = "sort",defaultValue = "id") String sort){

        return bookService.fetchAll(pageNo,pageSize,sort);
    }


    @GetMapping(path = "/fetchByTitle/{title}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public BookDto fetchByTitle(@PathVariable String title){
        return bookService.fetchByTitle(title);

    }

    @GetMapping(path = "/fetchByTitleNormal/{title}")
    public Book fetchByTitleNormal(@PathVariable String title){
        return bookService.fetchByTitleNormal(title);

    }

    @GetMapping(path = "/fetchByAuthor/{author}")
    public List<BookDto> fetchByAuthor(@PathVariable String author){
        return bookService.fetchByAuthor(author);

    }



    @GetMapping(path = "/fetchById/{id}")
    public ResponseEntity<BookDto> fetchById(@PathVariable Long id){

        BookDto bookDto1 = bookService.fetchById(id);

        return ResponseEntity.ok(bookDto1);
    }


    @PostMapping(path = "createBook")
    public Book createBook(@RequestBody BookDto bookDto){
        return bookService.createBook(bookDto);

    }

    @DeleteMapping(path = "deleteBook")
    public boolean book(@RequestBody Book book){
        return bookService.removeBook(book);

    }








}
