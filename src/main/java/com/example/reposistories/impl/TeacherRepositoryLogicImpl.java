package com.example.reposistories.impl;


import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.mapping.mappers.TeacherMapper;
import com.example.domain.model.Teacher;
import com.example.exceptions.UniversityException;
import com.example.reposistories.Repository;

import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryLogicImpl implements Repository<TeacherDto> {
    private List<Teacher> teachers;

    public TeacherRepositoryLogicImpl() {
        Teacher t1 = new Teacher(1L,"Monica", "1234@cue.edu.co");
        Teacher t2 = new Teacher(2L,"Andres", "1234@cue.edu.co");
        Teacher t3 = new Teacher(3L,"Likun","1234@cue.edu.co");
        teachers = new ArrayList<>(List.of(t1, t2, t3));
    }

    @Override
    public List<TeacherDto> list() {
        return TeacherMapper.mapFrom(teachers);
    }

    @Override
    public TeacherDto byId(Long id) {
        return teachers.stream()
                .filter(e->e.getId() == (e.getId()))
                .findFirst()
                .map(TeacherMapper::mapFrom)
                .orElseThrow(()-> new UniversityException("Teacher not found"));

    }

    @Override
    public void update(TeacherDto teacher) {
        TeacherMapper.mapFrom(teachers);
    }

    @Override
    public void delete(Long id) {
        TeacherMapper.mapFrom(teachers);
    }
}