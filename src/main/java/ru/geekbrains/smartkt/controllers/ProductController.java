package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dao.Product;
import ru.geekbrains.smartkt.services.ProductService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;

// 2. Сделать RestController позволяющий выполнять следующий набор операции над этой сущностью
// TODO: Переделать под новую логику фронта (Thymeleaf -> AngularJS)
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    // 2a) получение товара по id [ GET .../app/products/{id} ]
    // отдельно отобразить товар с указанным id
    @GetMapping("/products/get/{id}")
    public /*Product*/String getProduct(@PathVariable Integer id, Model model) {
        String s = "Информация о товаре с id="+id;
        try {
            Product p = service.getProduct(id);
            model.addAttribute("oneOrAll", s);
            model.addAttribute("productsList",
                    new ArrayList<>(Collections.singletonList(p)));
            return "products.html";
        }
        catch (RuntimeException ex) {
            model.addAttribute("msg", s + " отсутствует");
            return "msg_page.html";
        }
    }

    // 2b) получение всех товаров [ GET .../app/products ]
    // страница, на которой отображаются все товары из репозитория
    @GetMapping("/products/all")
    public /*List<Product>*/String getAllProducts(Model model) {
        model.addAttribute("oneOrAll", "Доступные товары");
        model.addAttribute("productsList", service.getAllProducts());
        return "products.html";
    }

    // Сделать форму для добавления товара в репозиторий и логику работы этой формы
    /*@GetMapping("/newProduct")
    public String addNewProduct(Model model) {
        // созданный здесь объект Product будет передан страницей по адресу /AddProduct
        model.addAttribute("product", new Product());
        model.addAttribute("actionType", "добавить новый товар");
        return "new_product.html";
    }*/

    // 2c) создание нового товара [ POST .../app/products ]
    // добавлять товар нужно post- (!не get-) запросом
    // аннотация @RequestBody для аргумента не нужна
    @PostMapping("/")
    public /*void*/String addProduct(@RequestParam(name = "id") Integer id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "cost") Integer cost) {
        service.addProduct(new Product(id, title, cost));
        // для вывода обновленного списка доступных товаров вместо вызова getAllProducts()
        // нужно использовать перенаправление на отображающую список страницу
        return "redirect:/products";
    }

    // 2d) удаление товара по id [ GET .../app/products/delete/{id} ]
    // метод DELETE логичнее выполнять по аннотации @DeleteMapping, но как реализовать это в Angular не ясно
    @GetMapping("/products/delete/{id}")
    public /*void*/String delProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }
}