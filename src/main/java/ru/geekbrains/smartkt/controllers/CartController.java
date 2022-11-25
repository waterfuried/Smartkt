package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dto.*;
import ru.geekbrains.smartkt.util.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private Cart cart;

    @GetMapping
    public /*Page*/List<OrderedProductDTO> getAll(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "cost") Integer cost) {
        return cart.getProducts();
    }

    // добавление в корзину
    // не соображу, как (в какой форме) передать добавленый в корзину товар сюда
    @PostMapping
    public void add(@RequestBody ProductDTO product) { cart.add(product); }

    // удаление из корзины
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { cart.delete(id); }
}