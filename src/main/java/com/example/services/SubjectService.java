package com.example.services;
import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> list();
    SubjectDto byId(Long id);
    void update(SubjectDto t);
    void delete(Long id);

}