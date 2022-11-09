package ru.geekbrains.smartkt.dao;

import java.util.*;

// интерфейс для логики выполнения CRUD-операций над сущностью
public interface Daocism<T> {
    T findById(Integer id);
    List<T> findAll();
    T saveOrUpdate(T t);
    void deleteById(Integer id);
}