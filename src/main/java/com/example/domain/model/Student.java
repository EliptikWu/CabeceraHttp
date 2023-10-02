package com.example.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Student {

    private Long idStu;
    private String name;
    private String email;
    private String semester;
}
