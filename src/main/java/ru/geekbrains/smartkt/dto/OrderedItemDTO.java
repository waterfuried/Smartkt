package ru.geekbrains.smartkt.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true) // создает конструктор копирования
public class OrderedItemDTO {
    private Integer id,         // id заказанного товара
                    //orderId,
                    productId,  // артикул товара
                    cost,       // цена товара (со склада - до учета скидок, доставок и т.п.)
                    quantity,   // кол-во товара
                    totalCost;  // цена товара с учетом скидок
    private String title;       // наименование товара
    //public Timestamp addedOn;

    public void incQuantity(int amount) { quantity += amount; }
    public void decQuantity(int amount) { quantity -= amount; }
}