package com.bookstore.com.controller.studentController;

import com.bookstore.com.domain.student.Student;
import com.bookstore.com.dto.studentDto.StudentDto;
import com.bookstore.com.services.studentService.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "student")
public class StudentController {
    private static final Logger  logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @GetMapping( path = "/fetchAll")
    public List<StudentDto> fetchAllStudent(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                                            @RequestParam(value = "sort",defaultValue = "age") String sort){

        return studentService.fetchAllStd(pageNo,pageSize,sort);
    }


    @GetMapping(path = "/fetchByName/{name}")
    public StudentDto fetchByTitle(@PathVariable String name){
        return studentService.fetchByName(name);

    }


    @GetMapping(path = "/fetchById/{id}")
    public StudentDto fetchById(@PathVariable Long id){
        return studentService.fetchById(id);
    }

    @DeleteMapping(path = "removeStudent")
    public boolean delStudent(@RequestBody StudentDto studentDto){
        logger.info("removing student...");
        return studentService.removeStudent(studentDto);

    }

    @PostMapping(path = "createStudent")
    public Student createStudent(@RequestBody StudentDto studentDto){
        logger.info("creating new student...");
        return studentService.createStudent(studentDto);




    }

}
