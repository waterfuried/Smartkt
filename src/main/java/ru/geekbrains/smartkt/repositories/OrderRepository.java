package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.orders.CustomOrder;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

//import javax.annotation.PostConstruct;
//import java.util.stream.*;

@Repository
public /*class*/interface OrderRepository extends JpaRepository<CustomOrder, Integer> {
    List<CustomOrder> findAllOrdersByCustomerId(Integer customerId);
    void deleteById(Integer id);

    /*private List<CustomOrder> orders;

    @PostConstruct
    public void init() { orders = new ArrayList<>(); }

    public CustomOrder find(int id) throws RuntimeException {
        return orders.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    public List<CustomOrder> getOrders() { return orders; }

    public List<CustomOrder> findAllOrdersByCustomer(Integer customerId) {
        return orders.stream().filter(p -> p.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

    public void add(CustomOrder customOrder) {
        if (orders.stream().filter(p -> p.getId().equals(customOrder.getId()))
                .findFirst().orElse(null) == null) orders.add(customOrder);
    }

    public void delete(Integer id) throws RuntimeException { orders.remove(find(id)); }*/
}