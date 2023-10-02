package com.example.domain.mapping.mappers;


import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.model.Subject;

import java.util.List;

public class SubjectMapper {

    public static SubjectDto mapFrom(Subject source){
        return new SubjectDto(source.getIdSub(),
                source.getName(),
                source.getTeacher());
    }

    public static Subject mapFrom(SubjectDto source){
        return new Subject(source.idSubject(),
                source.name(),
                source.teacher());
    }

    public static List<SubjectDto> mapFrom(List<Subject> sources){
        return sources.parallelStream().map(e-> mapFrom(e)).toList();
    }

}
