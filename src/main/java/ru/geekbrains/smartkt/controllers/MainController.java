package ru.geekbrains.smartkt.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.*;

import ru.geekbrains.smartkt.util.AOPStatistics;

@RestController // совмещает аннотации @Controller и @ResponseBody
@RequiredArgsConstructor
public class MainController {
    private final AOPStatistics statistics;

    @GetMapping // http://localhost:8189/app
    public String initApp() { return "<h1>Hello from Smartkt!</h1>"; }

    // 2.3. С помощью Spring AOP посчитайте по каждому сервису суммарное время,
    //      затрачиваемое на выполнение методов этих сервисов.
    //      И по endpoint'у /statistic выдайте полученную статистику клиенту.
    @GetMapping("/statistics")
    public String getServiceExecutionTimes() {
        return statistics.getServicesExecutionTimes().replaceAll("\n", "<br>");
    }
}