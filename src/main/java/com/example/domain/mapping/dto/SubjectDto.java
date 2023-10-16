package com.example.domain.mapping.dto;

import com.example.domain.model.Teacher;

public record SubjectDto(Long idSub,

                         String name,
                         Teacher teacher) {
}
