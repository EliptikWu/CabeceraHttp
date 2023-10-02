package com.example.domain.mapping.mappers;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;

import java.util.List;
public class StudentMapper {

    public static StudentDto mapFrom (Student source){
       return new StudentDto(source.getIdStu(),
               source.getName(),
               source.getEmail(),
               source.getSemester());
    }

    public static Student mapFrom (StudentDto source){
        return new Student(source.idStu(),
                source.name(),
                source.email(),
                source.semester());
    }

    public static List<StudentDto> mapFrom(List<Student> sources){
        return sources.parallelStream().map(e-> mapFrom(e)).toList();
    }

}
