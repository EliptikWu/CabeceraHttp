package com.example.services.impl;

import com.example.domain.mapping.dto.GradesDto;
import com.example.reposistories.Repository;
import com.example.services.GradesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
@ApplicationScoped

public class GradesServiceImpl implements GradesService {
    @Inject
    @Named("Grade")
    private Repository<GradesDto> repo;
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

