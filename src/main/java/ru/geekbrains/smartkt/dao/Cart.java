package ru.geekbrains.smartkt.dao;

import ru.geekbrains.smartkt.dao.orders.CustomOrder;

import javax.persistence.*;

import lombok.*;

// хранимая в БД корзина с заказом
// содержит только ссылку на заказ,
// надеюсь, этого будет достаточно - по заказу можно определить его покупателя
@Entity
@Table(name = "carts")
@Data
@RequiredArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id")
    CustomOrder order;
}