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
public class Subject implements Serializable {

    private Long idSub;
    private String name;
    private String teacher;

}
