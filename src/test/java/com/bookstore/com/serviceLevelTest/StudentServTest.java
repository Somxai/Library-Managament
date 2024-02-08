package com.bookstore.com.serviceLevelTest;


import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.StudentRepository;
import com.bookstore.com.domain.borrow.Borrow;
import com.bookstore.com.domain.student.Student;
import com.bookstore.com.dto.studentDto.StudentDto;
import com.bookstore.com.services.borrowService.BorrowService;
import com.bookstore.com.services.studentService.StudentService;
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
public class StudentServTest {


    private static final Logger logger = LoggerFactory.getLogger(StudentServTest.class);

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMappers modelMappers;

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private StudentService studentService;

    private List<Student> studentList;

    private List<StudentDto> studentDtoList;

    public Student student;

    private StudentDto studentDto;

    private Borrow borrow;

    @BeforeEach
    public void setUp(){

        student = new Student(1L,"Mik", "Inthavong", "Male", 33, "Mik@gmail.com",null);

        studentDto = new StudentDto(1L,"Mik", "Inthavong", "Male", 33, "Mik@gmail.com",null);

        studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Mik", "Inthavong", "Male", 33, "Mik@gmail.com",null));
        studentList.add(new Student(2L,"Max", "Inthavong", "Male", 29, "Max@gmail.com",null));
        studentList.add(new Student(3L,"Mike", "Inthavong", "Male", 30, "Mike@gmail.com",null));


        studentDtoList = new ArrayList<>();
        studentDtoList.add(new StudentDto(1L,"Mik", "Inthavong", "Male", 33, "Mik@gmail.com",null));
        studentDtoList.add(new StudentDto(2L,"Max", "Inthavong", "Male", 29, "Max@gmail.com",null));
        studentDtoList.add(new StudentDto(3L,"Mike", "Inthavong", "Male", 30, "Mik@gmail.com",null));


    }


    @Test
    public void isFetchByNameValid(){

        //arrange
        Mockito.when(studentRepository.fetchByName("Mik")).thenReturn(Optional.of(student).get());
        Mockito.when(modelMappers.studentToDTO(student)).thenReturn(studentDto);

        //act
        StudentDto studentFetchedByName = studentService.fetchByName("Mik");

        //assert
        Assertions.assertNotNull(studentFetchedByName);

    }

    @Test
    public void isFetchAllStdValid(){

        //arrange
        Pageable pageable = PageRequest.of(0, 3, Sort.by("age").descending());
        Page<Student> expectedStudent = new PageImpl<>(studentList,pageable,studentList.size());

        Mockito.when(studentRepository.findAll(pageable)).thenReturn(expectedStudent);

        Mockito.when(modelMappers.studentToDTO(studentList.get(0))).thenReturn(studentDtoList.get(0));
        Mockito.when(modelMappers.studentToDTO(studentList.get(1))).thenReturn(studentDtoList.get(1));
        Mockito.when(modelMappers.studentToDTO(studentList.get(2))).thenReturn(studentDtoList.get(2));


        //act
        List<StudentDto> stdResult = studentService.fetchAllStd(0,3,"age");

        Assertions.assertNotNull(stdResult);

    }

    @Test
    public void isFetchByIdValid(){
        //arrange
        Mockito.when(studentRepository.fetchById(1L)).thenReturn(Optional.of(student));
        Mockito.when(modelMappers.studentToDTO(student)).thenReturn(studentDto);

        //act
        logger.info("fetching in test level.");
        StudentDto studentDto1 = studentService.fetchById(1L);

        Assertions.assertNotNull(studentDto1);
    }

    @Test
    public void isCreateStudentValid(){

        //arrange
        Mockito.when(modelMappers.DtoToStudent(studentDto)).thenReturn(student);
        Mockito.when(studentRepository.fetchById(1L)).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        //act
        Student student1 = studentService.createStudent(studentDto);
        logger.info(" student: " + student1);

        Assertions.assertNotNull(student1);

    }

    @Test
    public void isRemoveValid(){

        //arrange
        Mockito.when(studentRepository.fetchById(1L)).thenReturn(Optional.of(student));
        Mockito.doNothing().when(studentRepository).delete(student);

        //act
        boolean removed = studentService.removeStudent(studentDto);

        Assertions.assertTrue(removed);


    }


}
