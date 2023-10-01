package com.example.reposistories.impl;

import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.mappers.GradesMapper;
import com.example.domain.model.Grades;
import com.example.domain.model.Student;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.reposistories.Repository;

import java.util.List;

public class GradesRepositoryLogicImpl implements Repository<GradesDto> {

    private List<Grades> grades;

    public GradesRepositoryLogicImpl(){
        Teacher t1 = new Teacher(1L, "Juan","1234@cue.edu.com");
        Teacher t2 = new Teacher(2L, "Pepito","1234@cue.edu.com");

        Subject sub1 = new Subject(1L,"Programación 1","Juan");
        Subject sub2 = new Subject(2L,"Programación 2", "Pepito");

        Student s1 = new Student(1L,"Li", "li@cue.edu.com", "1");
        Student s2 = new Student(2L,"Kun", "kune@cue.edu.com", "2");

        Grades g1 = new Grades(1L,s1,sub1,5.0);
        Grades g2 = new Grades(2L,s2,sub2,4.5);
    }
    @Override
    public List<GradesDto> list() {
        return GradesMapper.mapFrom(grades);
    }

    @Override
    public GradesDto byId(Long id) {
        return null;
    }

    @Override
    public void update(GradesDto gradesDto) {
        GradesMapper.mapFrom(grades);
    }

    @Override
    public void delete(Long id) {
        GradesMapper.mapFrom(grades);
    }
}
