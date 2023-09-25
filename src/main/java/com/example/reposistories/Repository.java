package com.example.reposistories;

import com.example.domain.mapping.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{

    List<T> listar();

    T porId(Long id);

    void guardar(T t);

    void eliminar(Long id);
}
