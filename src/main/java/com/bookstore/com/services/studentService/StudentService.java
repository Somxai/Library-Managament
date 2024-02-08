package com.bookstore.com.services.studentService;


import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.StudentRepository;
import com.bookstore.com.domain.student.Student;
import com.bookstore.com.dto.studentDto.StudentDto;
import com.bookstore.com.exception.studentException.NoSuchStudentExistException;
import com.bookstore.com.exception.studentException.StudentAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class StudentService {


    private final ModelMappers modelMappers;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final  StudentRepository studentRepository;



    @Autowired
    public StudentService(ModelMappers modelMappers, StudentRepository studentRepository) {
        this.modelMappers = modelMappers;
        this.studentRepository = studentRepository;

    }


    @Transactional
    public Student createStudent(StudentDto studentDto) {
        try {
            if (studentDto == null) {

                throw new NoSuchStudentExistException("the student is null.");
            }
            logger.info("converting Student Dto to Student....");
            Student convertedStudent = modelMappers.DtoToStudent(studentDto);
            Optional<Student> studentOpt = studentRepository.fetchById(convertedStudent.getStd_id());
            if (studentOpt.isPresent()) {
                logger.info("student is already exist perform update instead");

            }
            return studentRepository.save(convertedStudent);

        } catch (StudentAlreadyExistException e) {
            throw new StudentAlreadyExistException("student already existed.");
        }

    }




    @Transactional
    public boolean removeStudent(StudentDto studentDto){
        try {
            if (studentDto == null){
                throw new NoSuchStudentExistException("the student Obj is null.");
            }
            StudentDto studentToDelete = fetchById(studentDto.getStd_id());
            if (studentToDelete != null){
                logger.info("performing remove student" + studentToDelete + "...");
                studentRepository.delete(modelMappers.DtoToStudent(studentToDelete));
                logger.info("remove completed.");
                return true;

            }else {
                return false;
            }

        }catch (NoSuchStudentExistException ex){
            throw new NoSuchStudentExistException("there is no student to delete in database.");
        }

    }

    @Transactional
    @Cacheable(value = "student",key = "#id")
    public StudentDto fetchById(Long id){
        try{
            logger.info("fetching student......");
            Optional<Student> studentOptional = studentRepository.fetchById(id);
            Student student = studentOptional.orElseThrow(() -> new NoSuchStudentExistException("student fetching error"));
            logger.info("student fetched... " + student);
            return modelMappers.studentToDTO(student);


        }catch (NoSuchStudentExistException st){

            throw new NoSuchStudentExistException("the student with ID: " + id + " is not found");

        }

    }

    @Transactional
    @Cacheable(value = "student",key = "#id")
    public Student fetchByID(Long id){
        try{
            logger.info("fetching student......");
            Optional<Student> studentOptional = studentRepository.fetchById(id);
            Student student = studentOptional.orElseThrow(() -> new NoSuchStudentExistException("student fetching error"));
            logger.info("student fetched... " + student);
            return student;


        }catch (NoSuchStudentExistException st){

            throw new NoSuchStudentExistException("the student with ID: " + id + " is not found");

        }

    }



    @Transactional
    @Cacheable(value = "student",key = "#studentName")
    public StudentDto fetchByName(String studentName){
        try {
            logger.info("retrieving Student by name from Database....");
            Student student = studentRepository.fetchByName(studentName);
            logger.info("Student after retrieved " + student);
            //convert stdDto to studentObj
            logger.info("converting StudentDto to Student");
            StudentDto studentDto = modelMappers.studentToDTO(student);

            logger.info("studentDto after mapped: " + studentDto.toString());
            return studentDto;

        }catch (NoSuchStudentExistException e){

            logger.error("Something went wrong in the process" + e.getMessage());
            throw new NoSuchStudentExistException("this student is not valid in database");
        }
    }

    @Transactional
//    @Cacheable(value = "student",key = "#studentName")
    public Student fetchByNameNormal(String studentName){
        try {
            logger.info("retrieving Student by name from Database....");
            Student student = studentRepository.fetchByName(studentName);
            logger.info("Student after retrieved " + student);
            //convert stdDto to studentObj
            logger.info("converting StudentDto to Student");
            logger.info("studentDto after mapped: " + student.toString());
            return student;

        }catch (NoSuchStudentExistException e){

            logger.error("Something went wrong in the process" + e.getMessage());
            throw new NoSuchStudentExistException("this student is not valid in database");
        }
    }


    @Transactional
    @Cacheable(value = "student")
    public List<StudentDto> fetchAllStd(int pageNo, int pageSize, String sorting) {

        try {
            logger.info("trying to fetch all the students...");
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorting).descending());
            Page<Student> studentPage = studentRepository.findAll(pageable);
            List<StudentDto> studentDtos = studentPage.stream()
                    .map(modelMappers::studentToDTO).collect(Collectors.toList());

            for (StudentDto studentDto : studentDtos) {

                logger.info("student list after map " + studentDto);
            }

            return studentDtos;

        } catch (NoSuchStudentExistException e) {
            throw new NoSuchStudentExistException("there are no student exist in database");

        }
    }

}
