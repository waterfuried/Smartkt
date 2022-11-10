package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dao.Product;
import ru.geekbrains.smartkt.services.ProductService;

import org.springframework.web.bind.annotation.*;

import lombok.*;

import java.util.*;

// Сделать RestController позволяющий выполнять набор операций над сущностью
// Реализуйте REST контроллер для работы с сущностью Product
// TODO: Переделать под новую логику фронта (Thymeleaf -> AngularJS)
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService service;

    //или без использования Ломбок можно оставить этот конструктор
    //public ProductController(ProductService service) { this.service = service; }

    // получение товара по id [ GET .../app/products/{id} ]
    // отдельно отобразить товар с указанным id
    @GetMapping("/products/get/{id}")
    public /*String*/Product getProduct(@PathVariable Integer id/*, Model model*/) {
        /*String s = "Информация о товаре с id="+id;
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
        }*/
        return service.getProduct(id);
    }

    // получение всех товаров [ GET .../app/products ]
    // страница, на которой отображаются все товары из репозитория
    @GetMapping("/products/all")
    public /*String*/List<Product> getAllProducts(/*Model model*/) {
        //model.addAttribute("oneOrAll", "Доступные товары");
        //model.addAttribute("productsList", service.getAllProducts());
        //return "products.html";
        return service.getAllProducts();
    }

    // Сделать форму для добавления товара в репозиторий и логику работы этой формы
    /*@GetMapping("/newProduct")
    public String addNewProduct(Model model) {
        // созданный здесь объект Product будет передан страницей по адресу /AddProduct
        model.addAttribute("product", new Product());
        model.addAttribute("actionType", "добавить новый товар");
        return "new_product.html";
    }*/

    // создание нового товара [ POST .../app/products ]
    // добавлять товар нужно post- (!не get-) запросом
    // аннотация @RequestBody для аргумента не нужна
    @PostMapping("/")
    public /*String*/void addProduct(@RequestBody Product product
            /*@RequestParam(name = "id") Integer id,
              @RequestParam(name = "title") String title,
              @RequestParam(name = "cost") Integer cost*/) {
        service.addProduct(product/*new Product(id, title, cost)*/);
        // для вывода обновленного списка доступных товаров вместо вызова getAllProducts()
        // нужно использовать перенаправление на отображающую список страницу
        //return "redirect:/products";
    }

    // удаление товара по id [ GET .../app/products/delete/{id} ]
    // метод DELETE логичнее выполнять по аннотации @DeleteMapping, но как реализовать это в Angular не ясно
    @DeleteMapping("/products/delete/{id}")
    public /*String*/void delProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        //return "redirect:/products";
    }
}