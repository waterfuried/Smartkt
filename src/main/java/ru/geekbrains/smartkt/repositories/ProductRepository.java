package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.Product;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

// Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары).
@Repository
// для использования сервиса спецификаций нужно наследоваться от него
// однако с добавлением спецификаций реализованные ранее методы становятся ненужными
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
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

    //методы, производящие изменение записей в таблицах БД, нужно помечать аннотацией @Modyfying

    //boolean productExists(String title);
}