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
public class Student implements Serializable {

    private Long idStu;
    private String name;
    private String email;
    private String semester;
}
