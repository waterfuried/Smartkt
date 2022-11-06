package ru.geekbrains.smartkt.dao;

import javax.persistence.*;

import lombok.*;

import java.util.*;

// 1. В базе данных необходимо реализовать возможность хранить информацию о покупателях (id, имя)
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    //списки заказов покупателей можно определить запросом
    //select customers.name, orders.id from orders inner join customers on customers.id = orders.customer_id
    @OneToMany(mappedBy= "customer") // связь с таблицей заказов (один-к-многим, покупатель-заказы)
    private List<Order> orders;

    @Override
    public String toString() {
        return "Customer:"+
		"\n\tid = "+id+
		"\n\tname = "+name+
		"\n\torders = "+orders;
    }
}