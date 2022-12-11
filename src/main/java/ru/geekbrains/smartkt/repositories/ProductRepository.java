package ru.geekbrains.smartkt.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import ru.geekbrains.smartkt.dao.items.StoredItem;

// Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары).
@Repository
/*
    1. для использования сервиса спецификаций нужно наследоваться от него
       однако с добавлением спецификаций реализованные ранее методы становятся ненужными
    2. JpaSpecificationExecutor - интерфейс, в котором объявлен метод findAll,
       реализация котрого, в свою очередь, необходима при наследовании от интерфейса JpaRepository
*/
public interface ProductRepository
        extends JpaRepository<StoredItem, Integer>, JpaSpecificationExecutor<StoredItem> {
    /*
        К запросу всех товаров добавьте возможность фильтрации по минимальной и максимальной цене,
             в трех вариантах:
                товары дороже min цены,
                товары дешевле max цены,
                или товары, цена которых находится в пределах min-max.
    */
    /*@Query("select p from StoredItem p where p.cost > :min")
    List<Item> findAllWithCostGreaterThan(Integer min);

    @Query("select p from StoredItem p where p.cost < :max")
    List<Item> findAllWithCostLessThan(Integer max);

    @Query("select p from StoredItem p where p.cost > :min and p.cost < :max")
    // можно использовать один метод (с необязательными аргументами) вместо трех,
    // но нужно определиться, как задавать значения аргументов по умолчанию -
    // как некие глобальные константы (в отдельном модуле/классе), или как
    // локальные константы метода
    List<Item> findAllByCostBetween(Integer min, Integer max);*/

    //методы, производящие изменение записей в таблицах БД, нужно помечать аннотацией @Modyfying

    //boolean productExists(String title);
}