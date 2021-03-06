package com.conference.dao;

import java.util.List;

public interface Dao<T> {
    void save(T entity);

    List<T> getAll();

    T findById(Integer id);

    void removeById(Integer id);
}
