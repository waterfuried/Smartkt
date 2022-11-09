package ru.geekbrains.smartkt.controllers;

import org.springframework.web.bind.annotation.*;

@RestController // делает ненужными аннотации @ResponseBody
public class MainController {
    // http://localhost:8189/app
    @GetMapping("")
    public String initApp() { return "<h1>Hello from Smartkt!</h1>"; }
}