package com.example.reposistories.impl;

import com.example.domain.model.Student;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.exceptions.UniversityException;
import com.example.reposistories.Repository;

import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryLogicImpl implements Repository<Subject> {
    private List<Subject> subjects;

    public SubjectRepositoryLogicImpl() {
        Subject su1 = new Subject(1L,"División", "Monica");
        Subject su2 = new Subject(2L,"Programación", "Juan");
        Subject su3 = new Subject(3L,"Básicas", "Alex");
        subjects = new ArrayList<>(List.of(su1, su2, su3));
    }

    @Override
    public List<Subject> listar() {
        return subjects;
    }

    @Override
    public Subject porId(Long id) {
        return subjects.stream()
                .filter(e->id.equals(e.getId()))
                .findFirst()
                .orElseThrow(()-> new UniversityException("Subject not found"));
    }

    @Override
    public void guardar(Subject subject) {
        subjects.add(subject);
    }

    @Override
    public void eliminar(Long id) {
        subjects.removeIf(e->e.getId().equals(id));
    }
}
