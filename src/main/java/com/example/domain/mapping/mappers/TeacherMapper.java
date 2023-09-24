package com.example.domain.mapping.mappers;


import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.model.Teacher;

import java.util.List;

public class TeacherMapper {

    public static TeacherDto mapFrom(Teacher source){
        return new TeacherDto(source.getId(),
                source.getName(),
                source.getEmail());
    }

    public static Teacher mapFrom(TeacherDto source){
        return new Teacher(source.idTeacher(),
                source.name(),
                source.email());
    }

    public static List<TeacherDto> mapFrom(List<Teacher> sources){
        return sources.parallelStream().map(e-> mapFrom(e)).toList();
    }
}
