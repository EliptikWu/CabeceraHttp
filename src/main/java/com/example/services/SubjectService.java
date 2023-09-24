package com.example.services;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;

import java.util.List;

public interface SubjectService {
    List<Subject> listar();

    Subject porId(Long id);

    void guardar(Subject t);

    void eliminar(Long id);
}
