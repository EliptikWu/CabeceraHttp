package com.example.domain.mapping.dto;

public record StudentDto(Long idStudent,
                         String name,
                         String email,
                         String semester
) {
}
