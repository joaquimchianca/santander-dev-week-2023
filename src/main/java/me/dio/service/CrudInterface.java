package me.dio.service;

import java.util.List;

public interface CrudInterface <T, ID>{
    List<T> listAll();
    T findById(ID id );
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
