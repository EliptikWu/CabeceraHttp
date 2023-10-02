package com.example.reposistories.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Subject;
import com.example.exceptions.UniversityException;
import com.example.reposistories.Repository;

import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryLogicImpl implements Repository<SubjectDto> {
    private List<Subject> subjects;

    public SubjectRepositoryLogicImpl() {
        Subject su1 = new Subject(1L,"División", "Monica");
        Subject su2 = new Subject(2L,"Programación", "Juan");
        Subject su3 = new Subject(3L,"Básicas", "Alex");
        subjects = new ArrayList<>(List.of(su1, su2, su3));
    }

    @Override
    public List<SubjectDto> list() {
        return SubjectMapper.mapFrom(subjects);
    }

    @Override
    public SubjectDto byId(Long id) {
        return subjects.stream()
                .filter(e->e.getIdSub() == (e.getIdSub()))
                .findFirst()
                .map(SubjectMapper::mapFrom)
                .orElseThrow(()-> new UniversityException("Subject not found"));    }

    @Override
    public void update(SubjectDto subjectDto) {
        SubjectMapper.mapFrom(subjects);

    }

    @Override
    public void delete(Long id) {
        SubjectMapper.mapFrom(subjects);

    }
}