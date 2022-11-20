package ru.geekbrains.smartkt.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDTO {
    private Integer id,
                   //orderId,
                   productId,
                   quantity;
    //public Timestamp addedOn;

    public void incQuantity() { quantity++; }
    public void decQuantity() { quantity--; }
}