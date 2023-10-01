package com.example.services.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.StudentRepositoryJdbcImpl;
import com.example.services.StudentService;

import java.sql.Connection;
import java.util.List;
public class StudentServiceImpl implements StudentService {

    private Repository<StudentDto> repo;
    public StudentServiceImpl(Connection connection) {
        this.repo = new StudentRepositoryJdbcImpl(connection);
    }

    @Override
    public List<StudentDto> list() {
        return repo.list();
    }

    @Override
    public StudentDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(Student student) {
        repo.update(student);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}