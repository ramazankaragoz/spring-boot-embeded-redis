package com.ramazan.springdataredis.controller;

import com.ramazan.springdataredis.model.Student;
import com.ramazan.springdataredis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody Student save(@RequestBody Student student)
    {
        return studentService.save(student);
    }


    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public @ResponseBody
    List<Student> findAll()
    {
        return studentService.findAll();
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        studentService.delete(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public @ResponseBody  Student update(@RequestBody Student student)
    {
        return studentService.update(student);
    }
}
