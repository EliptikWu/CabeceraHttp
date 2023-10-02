package com.example.services.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.model.Subject;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.SubjectRepositoryImpl;
import com.example.services.SubjectService;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.util.List;
@NoArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private Repository<SubjectDto> repo;

    public SubjectServiceImpl(Connection connection){
        this.repo = new SubjectRepositoryImpl(connection);
    }
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
