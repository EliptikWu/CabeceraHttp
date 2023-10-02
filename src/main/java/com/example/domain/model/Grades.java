package com.example.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Grades {

    private Long idGra;
    private Student student;
    private Subject subject;
    private double grade;

}