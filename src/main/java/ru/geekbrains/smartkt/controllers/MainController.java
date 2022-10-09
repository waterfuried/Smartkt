package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dto.Product;
import ru.geekbrains.smartkt.services.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService service;

    // http://localhost:8189/app
    @GetMapping("")
    @ResponseBody
    public String hello(){
        return "<h1>Hello from smartkt!</h1>";
    }

    // 3. Сделать страницу, на которой отображаются все товары из репозитория.
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("oneOrAll", "Доступные товары");
        model.addAttribute("productsList", service.getAllProducts());
        return "products.html";
    }

    // отдельно отобразить товар с указанным id
    @GetMapping("/get/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
        String s = "Информация о товаре с id="+id;
        try {
            service.getProduct(id);
            model.addAttribute("oneOrAll", s);
            model.addAttribute("productsList",
                    new ArrayList<>(Collections.singletonList(service.getProduct(id))));
            return "products.html";
        }
        catch (RuntimeException ex) {
            model.addAttribute("msg", s + " отсутствует");
            return "msg_page.html";
        }
    }

    // ***. Сделать форму для добавления товара в репозиторий и логику работы этой формы;
    // TODO: сделал частично - не понял, как введенные оператором данные о товаре
    //  добавить в список доступных товаров (репозиторий)
    @GetMapping("/newProduct")
    public String addNewProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("actionType", "добавить новый товар");
        return "new_product.html";
    }
}