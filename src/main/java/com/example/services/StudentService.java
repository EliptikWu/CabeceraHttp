package com.example.services;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;

import java.util.List;

public interface StudentService {
    List<StudentDto> list();
    StudentDto byId(Long id);
    void update(Student t);
    void delete(Long id);

}
