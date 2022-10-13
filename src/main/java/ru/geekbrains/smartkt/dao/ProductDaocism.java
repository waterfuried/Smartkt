package ru.geekbrains.smartkt.dao;

import java.util.*;

// 2... реализуйте логику выполнения CRUD-операций над сущностью Product
public interface ProductDaocism {
    Product findById(Integer id);
    Product findByTitle(String title);
    List<Product> findAll();
    Product saveOrUpdate(Product product);
    void deleteById(Integer id);
}