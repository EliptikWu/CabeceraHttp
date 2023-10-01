package com.example.reposistories;

import com.example.domain.mapping.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{

    List<T> list();

    T byId(Long id);

    void update(T t);

    void delete(Long id);
}
