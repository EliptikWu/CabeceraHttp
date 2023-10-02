package com.example.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Subject {

    private Long idSub;
    private String name;
    private String teacher;

}
