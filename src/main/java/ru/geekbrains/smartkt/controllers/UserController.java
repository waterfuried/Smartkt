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

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping
    // 10.2. Создать страницу со списком всех пользователей, к которой имеют доступ только админы.
    public List<UserDTO> getAllUsers() { return service.getUsersList(); }

    //10.3. * Добавить роль суперадмина и дать ему возможность создавать новых пользователей
    //        и указывать роли существующим.
    @Secured({"ROLE_SUPERADMIN"})
    @PostMapping
    public UserDTO addUser(@RequestBody UserDTO user) {
        user.setId(null);
        user.validate();
        return new UserDTO(service.add(new Customer(user)));
    }
}