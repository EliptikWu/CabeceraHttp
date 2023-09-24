package com.example.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Student {

    private Long id;
    private String name;
    private String email;
    private String semester;
}
