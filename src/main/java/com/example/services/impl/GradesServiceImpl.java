package com.example.services.impl;

import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.dto.StudentDto;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.GradesRepositoryImpl;
import com.example.reposistories.impl.StudentRepositoryJdbcImpl;
import com.example.services.GradesService;

import java.sql.Connection;
import java.util.List;

public class GradesServiceImpl implements GradesService {
    private Repository<GradesDto> repo;
    public GradesServiceImpl(Connection connection) {
        this.repo = new GradesRepositoryImpl(connection);
    }

    @Override
    public List<GradesDto> list() {
        return repo.list();
    }

    @Override
    public GradesDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(GradesDto grades) {
        repo.update(grades);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}

