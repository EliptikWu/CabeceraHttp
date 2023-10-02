package com.example.services;

import com.example.domain.mapping.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> list();
    StudentDto byId(Long id);
    void update(StudentDto t);
    void delete(Long id);

}
