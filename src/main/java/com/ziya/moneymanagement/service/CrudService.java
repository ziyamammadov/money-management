package com.ziya.moneymanagement.service;

import java.util.Collection;

public interface CrudService<T> {
    Collection<T> getAll();

    T getOne(Long id);

    T save(T t);

    T update(T t);

    T deleteById(Long id);
}
