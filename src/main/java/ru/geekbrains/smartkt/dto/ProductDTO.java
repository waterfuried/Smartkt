/*
    DTO - Data Transfer Object - шаблон проектирования,
    используется для передачи данных между подсистемами приложения.
    В Enterprise JavaBeans используется для сериализации
*/
package ru.geekbrains.smartkt.dto;

import ru.geekbrains.smartkt.dao.Product;
import ru.geekbrains.smartkt.exceptions.ValidationException;

import java.util.*;

import lombok.*;

// класс Товар (Product) с полями id, title, cost
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String title;
    private Integer cost;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost = product.getCost();
    }

    public void validate() {
        List<String> errors = new ArrayList<>();

        if (cost < 1) errors.add("Недопустимая цена товара: "+cost);
        if (title.isBlank()) errors.add("Продукт не может не иметь названия");

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}