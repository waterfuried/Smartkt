package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dao.users.Customer;
import ru.geekbrains.smartkt.dto.*;
import ru.geekbrains.smartkt.services.CustomerService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CustomerService service;

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    // Создать страницу со списком всех пользователей, к которой имеют доступ только админы.
    public List<UserDTO> getAllUsers() { return service.getUsersList(); }

    // Добавить роль суперадмина и дать ему возможность создавать новых пользователей
    // и указывать роли существующим.
    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping("/add")
    public UserDTO addUser(@RequestBody UserDTO user) {
        user.setId(null);
        user.validate();
        return new UserDTO(service.addOrUpdate(new Customer(user)));
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO user) {
        user.validate();
        return new UserDTO(service.addOrUpdate(new Customer(user)));
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) { service.delete(id); }
}