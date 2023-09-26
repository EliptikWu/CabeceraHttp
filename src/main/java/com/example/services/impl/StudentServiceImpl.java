package com.example.services.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.StudentRepositoryJdbcImpl;
import com.example.reposistories.impl.StudentRepositoryLogicImpl;
import com.example.services.StudentService;

import java.sql.SQLException;
import java.util.List;
public class StudentServiceImpl implements StudentService {
    private Repository<StudentDto> studentRepository;
    public StudentServiceImpl(StudentRepositoryLogicImpl repository) {
        this.studentRepository = new StudentRepositoryJdbcImpl(connection);
        this.repository = repository;
    }
    @Override
    public List<StudentDto> list() {
        try {
            return studentRepository.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(),
                    throwables.getCause());
        }
    }

    private final StudentRepositoryLogicImpl repository;
    @Override
    public List<Student> listar() {
        return repository.listar();
    }

    @Override
    public Student porId(Long id) {
        return repository.porId(id);
    }

    @Override
    public void guardar(Student t) {
        repository.guardar(t);
    }

    @Override
    public void eliminar(Long id) {
        repository.eliminar(id);
    }
}
