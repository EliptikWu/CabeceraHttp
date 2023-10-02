package com.example.domain.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Teacher {

    private Long idTea;
    private String name;
    private String email;
}
