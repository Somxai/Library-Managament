package com.bookstore.com.controller.authorController;

import com.bookstore.com.domain.author.Author;
import com.bookstore.com.dto.authorDto.AuthorDto;
import com.bookstore.com.services.authorService.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping(path = "/authors")
public class AuthorController {


    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping( path = "/fetchAll")
    public ResponseEntity<List<AuthorDto>> fetchAllAuthor(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                          @RequestParam(value = "sort",defaultValue = "firstName") String sort){

            return ResponseEntity.ok(authorService.fetchAll(pageNo, pageSize, sort));
        }


    @GetMapping(path = "/fetchById/{id}")
    public ResponseEntity<AuthorDto> fetchById(@PathVariable Long id){
        AuthorDto authorDto = authorService.fetchById(id);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping(path = "/fetchByName/{name}")
    public ResponseEntity<AuthorDto> fetchByName(@PathVariable String name){

        AuthorDto authorDto;
            authorDto = authorService.fetchByNameDto(name);
            return ResponseEntity.ok(authorDto);
    }
    @DeleteMapping(path = "removeAuthor")
    public ResponseEntity<Boolean> removeAuthor(@RequestBody AuthorDto authorDto){

            boolean remove = authorService.removeAuthor(authorDto);

            return ResponseEntity.ok(remove);

    }

    @PostMapping(path = "createAuthor")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDto authorDto){

        return ResponseEntity.ok(authorService.createAuthor(authorDto));
    }







}






