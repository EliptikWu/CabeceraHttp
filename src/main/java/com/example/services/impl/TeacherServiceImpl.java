package com.example.services.impl;

import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.model.Teacher;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.TeacherRepositoryImpl;
import com.example.services.TeacherService;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.util.List;
@NoArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private Repository<TeacherDto> repo;
    public TeacherServiceImpl(Connection connection) {
        this.repo = new TeacherRepositoryImpl(connection);
    }

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
