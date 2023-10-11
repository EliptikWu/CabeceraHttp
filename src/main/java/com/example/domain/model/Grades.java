package com.example.domain.model;

import jakarta.enterprise.context.SessionScoped;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@SessionScoped
public class Grades implements Serializable {

    private Long idGra;
    private Student student;
    private Subject subject;
    private double grade;

}