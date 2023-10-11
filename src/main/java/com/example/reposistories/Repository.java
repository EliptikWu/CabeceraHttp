package com.example.reposistories;

import java.util.List;

public interface Repository <T>{

    List<T> list();

    T byId(Long id);

    void update(T t);

    void delete(Long id);
}
