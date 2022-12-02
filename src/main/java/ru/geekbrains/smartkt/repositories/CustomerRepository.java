package ru.geekbrains.smartkt.repositories;

import ru.geekbrains.smartkt.dao.users.Customer;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

//import jakarta.annotation.PostConstruct; //import javax.annotation.PostConstruct;

import java.util.*;

@Repository
public /*class*/interface CustomerRepository extends JpaRepository<Customer, Integer> {
    /*private List<Customer> customers;

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

    public void delete(Integer id) throws RuntimeException { customers.remove(find(id)); }*/

    Optional<Customer> findByUsername(String name);

    List<Customer> findAll();
}