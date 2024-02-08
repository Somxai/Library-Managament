package com.bookstore.com.services.authorService;

import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.AuthorRepository;
import com.bookstore.com.domain.author.Author;
import com.bookstore.com.dto.authorDto.AuthorDto;
import com.bookstore.com.exception.authorException.AuthorAlreadyExistException;
import com.bookstore.com.exception.authorException.NoSuchAuthorExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);


    private final ModelMappers modelMapper;

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(@Lazy AuthorRepository authorRepository, ModelMappers modelMappers) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMappers;
    }

    @Transactional
    @Cacheable(value = "author", key = "#id")
    public AuthorDto fetchById(Long id){


            Optional<Author> author = authorRepository.fetchById(id);
            if (author.isEmpty()){
                logger.info("author is not exist");
                throw new NoSuchAuthorExistException("author id "+ id + " is not exist in database");
            }
            try {
                logger.info("start converting.... and show author before convert : " + author.get());
                AuthorDto authorDto = modelMapper.authorToDto(author.get());
                logger.info("after converted DTO: " + authorDto);
                return authorDto;

            }catch (NoSuchAuthorExistException e) {
            logger.error("error happen");
            throw  new NoSuchAuthorExistException("error occur while converting and return "+ e.getMessage());


        }

    }

    @Cacheable(value = "authors")
    public List<AuthorDto> fetchAll(int pageNo, int pageSize, String sort){
        try {
            Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sort));
            Page<Author> authors = authorRepository.findAll(pageable);
            logger.info("Starting to map author to dto" );
            List<AuthorDto> borrowDtos = authors.stream().map(modelMapper::authorToDto).
                    collect(Collectors.toList());
            for (AuthorDto borrowDto : borrowDtos){
                logger.info("author : " + borrowDto);

            }
            logger.info("after  mapped author to " + borrowDtos.size());

            return borrowDtos;

        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

    @Transactional
    @Cacheable(value = "author", key = "#name")
    public AuthorDto fetchByNameDto(String name){
        try {
            Author author = authorRepository.fetchByName(name);
            logger.info("author after fetchByName: " + author);
            AuthorDto borrowDto;
            borrowDto = modelMapper.authorToDto(author);
            return borrowDto;
        }catch (NoSuchAuthorExistException e){
            logger.error("fetch author error" , e);
            throw new NoSuchAuthorExistException("this author is not in database.");

        }
    }

    @Transactional
    @Cacheable(value = "books",key = "#name")
    public Author fetchByName(String name){
        try {
            Author author = authorRepository.fetchByName(name);
            logger.info("author after fetch from name: " + author);
            return authorRepository.fetchByName(name);

        }catch (NoSuchAuthorExistException e){
            logger.error("fetch author error" , e);
            throw new NoSuchAuthorExistException("this Author is not in database");

        }
    }

    @Transactional
    @Cacheable(value = "author", key = "#authorDto")
    public Author createAuthor(AuthorDto authorDto){
        try {
            Author author = modelMapper.DtoToAuthor(authorDto);
            return authorRepository.save(author);

        }catch (AuthorAlreadyExistException e){
            logger.error(e.getMessage());
            throw new AuthorAlreadyExistException("this author is already exist");
        }
    }

    public boolean removeAuthor(@NonNull AuthorDto author){

    Optional<Author> authorToRemove = authorRepository.fetchById(author.getAuthorId());
    try {

        return true;
    }catch (NoSuchAuthorExistException e){
        logger.error("Author ID:" + author.getAuthorId() + "to delete is not present", e);
        return false;
    }

    }

    } // end of code





