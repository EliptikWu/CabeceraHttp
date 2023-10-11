package com.example.services.impl;

import com.example.domain.mapping.dto.TeacherDto;
import com.example.reposistories.Repository;
import com.example.services.TeacherService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.util.List;
@NoArgsConstructor
@ApplicationScoped
public class TeacherServiceImpl implements TeacherService {
    @Inject
    @Named("Teacher")
    private Repository<TeacherDto> repo;
    @Override
    public List<TeacherDto> list() {
        return repo.list();
    }

    @Override
    public TeacherDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(TeacherDto teacher) {
        repo.update(teacher);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}
