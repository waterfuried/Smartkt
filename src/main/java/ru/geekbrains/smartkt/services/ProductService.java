package ru.geekbrains.smartkt.services;

import ru.geekbrains.smartkt.dao.Product;
import ru.geekbrains.smartkt.repositories.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product getProduct(int id) { return repository.findById(id).orElse(null); }

    public List<Product> getAllProducts() { return repository.findAll()/*getProducts()*/; }

    public void addProduct(Product product) { repository./*add*/save(product); }

    public void deleteProduct(Integer id) { repository./*delete*/deleteById(id); }
}