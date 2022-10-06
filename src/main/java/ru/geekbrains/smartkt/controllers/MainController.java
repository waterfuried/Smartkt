package ru.geekbrains.smartkt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService service;

    // http://localhost:8189/app
    @ResponseBody
    public String hello(){
        return "<h1>Hello from smartkt!</h1>";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        return "products.html";
    }

    @GetMapping("/newProduct")
    public String addNewProduct(Model model, @RequestParam int id) {
        model.addAttribute("studentFront", service.getStudent(id));
        return "new_product.html";
    }
}