package com.example.domain.mapping.dto;

import com.example.domain.model.Student;
import com.example.domain.model.Subject;

public record GradesDto(Long idGra,
                        Student student,
                        Subject subject,
                        double corte){

}
