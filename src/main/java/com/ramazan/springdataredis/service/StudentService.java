package com.ramazan.springdataredis.service;

import com.ramazan.springdataredis.model.Student;
import com.ramazan.springdataredis.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Cacheable(cacheNames = "students")
    public List<Student> findAll()
    {
        return (List<Student>) studentRepository.findAll();
    }

    @CachePut(cacheNames = "student", key = "'student#' + #student.id")
    @CacheEvict(cacheNames = "students", allEntries = true)
    public Student save(Student student)
    {

        return studentRepository.save(student);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "student", key = "'student#' + #id"),
            @CacheEvict(cacheNames = "students", allEntries = true) })
    public void delete(Long id)
    {
        studentRepository.deleteById(id);
    }



}
