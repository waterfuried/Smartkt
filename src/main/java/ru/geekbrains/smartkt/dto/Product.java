package ru.geekbrains.smartkt.dto;

import lombok.*;

// 1. Создать класс Товар (Product), с полями id, title, cost;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String title;
    private int cost;

    public static int randomNumber(int min, int max) {
        return min + Math.round((float)Math.random()*(max - min));
    }
}