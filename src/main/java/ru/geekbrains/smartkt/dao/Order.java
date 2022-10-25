package ru.geekbrains.smartkt.dao;

import javax.persistence.*;

import lombok.*;

import java.sql.Timestamp;
import java.util.*;

// класс заказа (покупатель делает заказ продуктов):
// - номер заказа
// - список товаров в заказе (внешний ключ)
// - покупатель customer (OneToMany)
// - дата и время заказа
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @OneToMany(mappedBy="order") // связь с таблицей продуктов (один-ко-многим, заказ-продукты)
    @Column(name = "product")
    private List<Product> products;

    @OneToMany(mappedBy="id") // связь с таблицей покупателей (один-ко-многим, заказ-покупатели)
    @Column(name = "customer")
    private List<Customer> customer;

    @Column(name = "date_time")
    private Timestamp date_time;

    @Override
    public String toString() {
        return "Order:"+
		"\n\tid="+id+
		"\n\tcustomer="+customer+
		"\n\tproducts="+products+
		"\n\ttimestamp="+date_time;
    }
}