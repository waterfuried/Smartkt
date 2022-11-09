package ru.geekbrains.smartkt.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public /*void*/ResponseEntity<?> addProduct(Product product) {
        //repository./*add*/save(product);
        /*if (repository.productExists(product.getTitle()))
            return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(),
                    "Product '"+product.getTitle()+"' already exists"), HttpStatus.CONFLICT);
        else */
            repository.save(product);
            return new ResponseEntity<>(HttpStatus.OK);
        //}
    }

    public void deleteProduct(Integer id) { repository./*delete*/deleteById(id); }
}