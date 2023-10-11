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
public class Teacher implements Serializable {

    private Long idTea;
    private String name;
    private String email;
}
