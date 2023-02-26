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

    // для всех операций с корзиной рекомендуется использовать только GET-запросы,
    // в том случае, если передавать в его теле не нужно никакие объекты
    @GetMapping
    public /*Page*/List<OrderedItemDTO> getAll(
            /*@RequestParam(name = "id") Integer id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "cost") Integer cost*/) {
        return cart.getContent();
    }

    // добавление в корзину
    // всегда происходит из списка товаров на складе
    @GetMapping("/add/{id}")
    public void add(@PathVariable Integer id) { cart.add(id); }

    // изменение кол-ва товара в корзине
    @GetMapping("/change_amount")
    public void changeAmount(@RequestParam Integer id, @RequestParam Integer delta) {
        cart.changeAmount(id, delta);
    }

    // удаление из корзины
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) { cart.delete(id); }

    // очистка корзины
    @GetMapping("/clear")
    public void clear() { cart.clear(); }
}