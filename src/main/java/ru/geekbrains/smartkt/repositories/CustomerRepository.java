package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.Customer;

import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;

import java.util.*;

@Repository
public class CustomerRepository {
    private List<Customer> customers;

    @PostConstruct
    public void init() { customers = new ArrayList<>(); }

    // поскольку имена покупателей могут не быть уникальными,
    // покупатель однозначно определяется только его идентификатором
    public Customer find(int id) throws RuntimeException {
        return customers.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Покупатель не найден"));
    }

    public Customer find(String name) throws RuntimeException {
        return customers.stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new RuntimeException("Покупатель не найден"));
    }

    public List<Customer> getCustomers() { return customers; }

    public void add(Customer customer) {
        try { Customer c = find(customer.getId()); }
        catch (RuntimeException ex) { customers.add(customer); }
    }

    public void delete(Integer id) throws RuntimeException { customers.remove(find(id)); }
}