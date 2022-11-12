package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

// Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары).
@Repository
public /*class*/ interface ProductRepository extends JpaRepository<Product, Integer> {
    /*private final String[] PRODUCT_TYPE = { "Вода", "Хлеб", "Картофель", "Молоко", "Яйцо куриное",
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
    public Product findById(int id) throws RuntimeException {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
    }

    public List<Product> getProducts() { return products; }

    public void add(Product product) {
        if (products.stream().filter(p -> p.getTitle().equals(product.getTitle()))
                .findFirst().orElse(null) == null) products.add(product);
    }

    public void delete(Integer id) throws RuntimeException { products.remove(findById(id)); }
    */

    /*
        К запросу всех товаров добавьте возможность фильтрации по минимальной и максимальной цене,
             в трех вариантах:
                товары дороже min цены,
                товары дешевле max цены,
                или товары, цена которых находится в пределах min-max.
    */
    @Query("select p from Product p where p.cost > :min")
    List<Product> findAllWithCostGreaterThan(Integer min);

    @Query("select p from Product p where p.cost < :max")
    List<Product> findAllWithCostLessThan(Integer max);

    @Query("select p from Product p where p.cost > :min and p.cost < :max")
    // можно использовать один метод (с необязательными аргументами) вместо трех,
    // но нужно определиться, как задавать значения аргументов по умолчанию -
    // как некие глобальные константы (в отдельном модуле/классе), или как
    // локальные константы метода
    List<Product> findAllByCostBetween(Integer min, Integer max);

    //boolean productExists(String title);
}