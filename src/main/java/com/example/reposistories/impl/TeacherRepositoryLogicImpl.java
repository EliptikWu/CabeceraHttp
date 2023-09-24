package com.example.reposistories.impl;


import com.example.domain.model.Teacher;
import com.example.exceptions.UniversityException;
import com.example.reposistories.Repository;

import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryLogicImpl implements Repository<Teacher> {
    private List<Teacher> teachers;

    public TeacherRepositoryLogicImpl() {
        Teacher t1 = new Teacher(1L,"Monica", "1234@cue.edu.co");
        Teacher t2 = new Teacher(2L,"Andres", "1234@cue.edu.co");
        Teacher t3 = new Teacher(3L,"Likun","1234@cue.edu.co");
        teachers = new ArrayList<>(List.of(t1, t2, t3));
    }

    @Override
    public List<Teacher> listar() {
        return teachers;
    }

    @Override
    public Teacher porId(Long id) {
        return teachers.stream()
                .filter(e->id.equals(e.getId()))
                .findFirst()
                .orElseThrow(()-> new UniversityException("Teacher not found"));
    }

    @Override
    public void guardar(Teacher teacher) {
        teachers.add(teacher);
    }

    @Override
    public void eliminar(Long id) {
        teachers.removeIf(e->e.getId().equals(id));
    }
}
