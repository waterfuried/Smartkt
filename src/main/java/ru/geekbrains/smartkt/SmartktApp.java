package ru.geekbrains.smartkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartktApp {
    public static void main(String[] args) {
        // временная проверка реализованных методов работы с БД
        /*
        SessionFactoryUtils productsSessionFactoryUtils = new SessionFactoryUtils();
        productsSessionFactoryUtils.init("hibernate.xml");
        SessionFactoryUtils ordersSessionFactoryUtils = new SessionFactoryUtils();
        ordersSessionFactoryUtils.init("hibernate.xml");
        try {
            ProductDao p = new ProductDao(productsSessionFactoryUtils);
            OrderDao r = new OrderDao(ordersSessionFactoryUtils);
            p.test();
            r.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            productsSessionFactoryUtils.shutdown();
            ordersSessionFactoryUtils.shutdown();
        }
         */
        SpringApplication.run(SmartktApp.class, args);
    }
}