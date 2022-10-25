package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.Order;

import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;

import java.util.*;

@Repository
public class OrderRepository {
    private List<Order> orders;

    @PostConstruct
    public void init() { orders = new ArrayList<>(); }

    public Order find(int id) throws RuntimeException {
        return orders.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    public List<Order> getOrders() { return orders; }

    public void add(Order order) {
        if (orders.stream().filter(p -> p.getId().equals(order.getId()))
                .findFirst().orElse(null) == null) orders.add(order);
    }

    public void delete(Integer id) throws RuntimeException { orders.remove(find(id)); }
}