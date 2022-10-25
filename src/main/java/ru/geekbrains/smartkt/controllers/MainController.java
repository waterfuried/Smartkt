package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dto.Product;
import ru.geekbrains.smartkt.services.ProductService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.*;

import java.util.*;

// TODO: Переделать под новую логику фронта с клиентов на продукты
@RestController // делает ненужными аннотации @ResponseBody
@RequiredArgsConstructor
public class MainController {
    private final ProductService service;

    // http://localhost:8189/app
    @GetMapping("")
    public String hello(){
        return "<h1>Hello from smartkt!</h1>";
    }

    // страница, на которой отображаются все товары из репозитория
    @GetMapping("/products/all")
    /*public String getAllProducts(Model model) {
        model.addAttribute("oneOrAll", "Доступные товары");
        model.addAttribute("productsList", service.getAllProducts());
        return "products.html";
    }*/
    public List<Product> getAllProducts() { return service.getAllProducts(); }

    // отдельно отобразить товар с указанным id
    // TODO: переделать под Angular
    @GetMapping("/products/get/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
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

    // Сделать форму для добавления товара в репозиторий и логику работы этой формы
    /*@GetMapping("/newProduct")
    public String addNewProduct(Model model) {
        // созданный здесь объект Product будет передан страницей по адресу /AddProduct
        model.addAttribute("product", new Product());
        model.addAttribute("actionType", "добавить новый товар");
        return "new_product.html";
    }*/

    // добавлять товар нужно post- (!не get-) запросом
    /*@PostMapping("/products/new")
    // аннотация @RequestBody для аргумента не нужна
    public void addProduct(Product product) {
        service.addProduct(product);
        // для вывода обновленного списка доступных товаров вместо вызова getAllProducts()
        // нужно использовать перенаправление на отображающую список страницу
        //return "redirect:/products";
    }*/
    @PostMapping("/")
    public void addProduct(@RequestParam(name = "id") Integer id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "cost") Integer cost) {
        service.addProduct(new Product(id, title, cost));
    }

    // метод DELETE логичнее выполнять по аннотации @DeleteMapping, но как реализовать это в Angular не ясно
    @GetMapping("/products/delete/{id}")
    public void delProduct(@PathVariable Integer id) { service.deleteProduct(id); }
}