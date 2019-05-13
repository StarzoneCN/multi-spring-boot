package com.example.demo.multi.springBoot.dao;

import com.example.demo.multi.springBoot.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
}
