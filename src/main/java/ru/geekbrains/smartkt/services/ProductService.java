package ru.geekbrains.smartkt.services;

import ru.geekbrains.smartkt.dto.Product;
import ru.geekbrains.smartkt.repositories.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product getProduct(int id) { return repository.findById(id); }

    public List<Product> getAllProducts() { return repository.getProducts(); }

    public void addProduct(Product product) { repository.add(product); }

    // 3) * Реализовать метод DELETE
    public void deleteProduct(Integer id) { repository.delete(id); }
}