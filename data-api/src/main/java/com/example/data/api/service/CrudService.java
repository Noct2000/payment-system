package com.example.data.api.service;

import java.util.List;

public interface CrudService<E> {
    List<E> findAll();

    E save(E entity);

    void deleteById(Long id);

    E getById(Long id);

    E update(E entity);
}
