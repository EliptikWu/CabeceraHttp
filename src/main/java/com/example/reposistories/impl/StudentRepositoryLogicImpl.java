package com.example.reposistories.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.mapping.mappers.StudentMapper;
import com.example.domain.model.Student;
import com.example.exceptions.UniversityException;
import com.example.reposistories.Repository;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryLogicImpl implements Repository<StudentDto> {
    private List<Student> students;

    public StudentRepositoryLogicImpl() {
        Student s1 = new Student(1L,"Monica", "1234@cue.edu.co", "Tercero");
        Student s2 = new Student(2L,"Pepe", "1234@cue.edu.co", "Segundo");
        Student s3 = new Student(3L,"Juan", "1234@cue.edu.c", "Primero");
        students = new ArrayList<>(List.of(s1, s2, s3));
    }

    @Override
    public List<StudentDto> list() {
        return StudentMapper.mapFrom(students);
    }

    @Override
    public StudentDto byId(Long id) {
        return students.stream()
                .filter(e->e.getIdStu() == (e.getIdStu()))
                .findFirst()
                .map(StudentMapper::mapFrom)
                .orElseThrow(()-> new UniversityException("Student not found"));
    }

    @Override
    public void update(StudentDto student) {
        StudentMapper.mapFrom(students);

    }

    @Override
    public void delete(Long id) {
        StudentMapper.mapFrom(students);

    }

}