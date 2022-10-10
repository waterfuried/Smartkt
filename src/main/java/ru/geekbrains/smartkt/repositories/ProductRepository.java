package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dto.Product;

import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;

import java.util.*;

// 2. Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары).
@Repository
public class ProductRepository {
    private final String[] PRODUCT_TYPE = { "Вода", "Хлеб", "Картофель", "Молоко", "Яйцо куриное",
            "Мясо", "Соль", "Сахар", "Помидоры", "Лук" };
    private final int[] PRODUCT_PRICE = { 20, 25, 30, 50, 55,
            200, 35, 70, 100, 40 };

    private List<Product> products;

    @PostConstruct
    public void init() {
        final int MAX_PROD = 5;
        products = new ArrayList<>();
        int id;
        for (int i = 0; i < MAX_PROD; i++) {
            boolean already;
            do {
                id = Product.randomNumber(0, PRODUCT_TYPE.length - 1);
                already = false;
                for (int j = 0; j < i && !already; j++)
                    already = products.get(j).getId() == id;
            } while (already);
            products.add(new Product(id, PRODUCT_TYPE[id], PRODUCT_PRICE[id]));
        }
    }

    // Репозиторий должен уметь выдавать список всех товаров и товар по id;
    public Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
    }

    public List<Product> getProducts(){
        return products;
    }

    public void add(Product product) {
        if (products.stream().filter(p -> p.getTitle().equals(product.getTitle()))
                .findFirst().orElse(null) == null) products.add(product);
    }
}