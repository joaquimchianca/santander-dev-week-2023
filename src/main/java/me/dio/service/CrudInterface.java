package me.dio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudInterface <T, ID>{
    Page<T> listAll(Pageable page);
    T findById(ID id );
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
