package ru.geekbrains.smartkt.services;

import ru.geekbrains.smartkt.dao.Customer;
import ru.geekbrains.smartkt.repositories.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private CustomerRepository repository;

    @Autowired
    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer find(String name) throws RuntimeException {
        return repository.find(name);
    }

    public void add(Customer customer) { repository.add(customer); }

    public void delete(Integer id) { repository.delete(id); }
}