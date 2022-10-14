/*
    DTO - Data Transfer Object - шаблон проектирования,
    используется для передачи данных между подсистемами приложения.
    В Enterprise JavaBeans используется для сериализации
*/
package ru.geekbrains.smartkt.dto;

import lombok.*;

// класс Товар (Product) с полями id, title, cost
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String title;
    private Integer cost;

    public static int randomNumber(int min, int max) {
        return min + Math.round((float)Math.random()*(max - min));
    }
}