package com.bookstore.com.configPackage;


import com.bookstore.com.domain.author.Author;
import com.bookstore.com.domain.book.Book;
import com.bookstore.com.domain.borrow.Borrow;
import com.bookstore.com.domain.student.Student;
import com.bookstore.com.dto.authorDto.AuthorDto;
import com.bookstore.com.dto.bookDto.BookDto;
import com.bookstore.com.dto.borrowDto.BorrowDto;
import com.bookstore.com.dto.studentDto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class ModelMappers {
    @Bean
     public ModelMapper modelMapper(){
                return new ModelMapper();
        }

        public BookDto bookToDTO(Book book){

            BookDto bookDto = new BookDto();
            bookDto = modelMapper().map(book, BookDto.class);
            if (book.getAuthor() != null){
                bookDto.setAuthorName(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
            }
            return bookDto;
        }

        public Book DtoToBook(BookDto bookDto){
        Book book = new Book();
        book = modelMapper().map(bookDto, Book.class);
        return book;
        }

        public AuthorDto authorToDto(Author author){

        return modelMapper().map(author, AuthorDto.class);
        }

        public Author DtoToAuthor(AuthorDto authorDto){

        return modelMapper().map(authorDto, Author.class);
    }

        public BorrowDto borrowToDTO(Borrow borrow){

       BorrowDto borrowDto = modelMapper().map(borrow, BorrowDto.class);
            if (borrowDto.getBooksTitle() != null){
                borrowDto.setBooksTitle(borrow.getBooks().getTitle());

            }

        if (borrowDto.getStudentsName() != null){
            borrowDto.setStudentsName(borrow.getStudents().getFirstName());
        }


        return borrowDto;
    }

        public Borrow DtoToBorrow(BorrowDto borrowDto){

        return modelMapper().map(borrowDto, Borrow.class);
    }

    public StudentDto studentToDTO(Student student){

        StudentDto studentDto = modelMapper().map(student, StudentDto.class);
        if (student.getBorrowStd() != null){
            studentDto.borrowedBooks(student.getBorrowStd().stream().map(title ->
                    title.getBooks().getTitle()).collect(Collectors.toList()));
        }
        return studentDto;
    }

    public Student DtoToStudent(StudentDto studentDto){

        return modelMapper().map(studentDto, Student.class);
    }
}
