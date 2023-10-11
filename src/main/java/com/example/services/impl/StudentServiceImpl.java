package com.example.services.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.reposistories.Repository;
import com.example.services.StudentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@ApplicationScoped
public class StudentServiceImpl implements StudentService {
    @Inject
    @Named("Student")
    private Repository<StudentDto> repo;
    @Override
    public List<StudentDto> list() {
        return repo.list();
    }

    @Override
    public StudentDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(StudentDto student) {
        repo.update(student);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}