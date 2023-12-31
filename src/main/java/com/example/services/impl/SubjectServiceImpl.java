package com.example.services.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.reposistories.Repository;
import com.example.services.SubjectService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@ApplicationScoped

public class SubjectServiceImpl implements SubjectService {
    @Inject
    @Named("Subject")
    private Repository<SubjectDto> repo;

    @Override
    public List<SubjectDto> list() {
        return repo.list();
    }

    @Override
    public SubjectDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(SubjectDto subject) {
        repo.update(subject);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}
