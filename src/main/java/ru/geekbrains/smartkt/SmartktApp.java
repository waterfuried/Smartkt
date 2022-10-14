package ru.geekbrains.smartkt;

import ru.geekbrains.smartkt.dao.ProductDao;
import ru.geekbrains.smartkt.services.hibernate.SessionFactoryUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartktApp {
    public static void main(String[] args) {
        // временная проверка реализованных методов работы с БД
        SessionFactoryUtils sessionFactoryUtils = new SessionFactoryUtils();
        sessionFactoryUtils.init("hibernate.xml");
        try {
            ProductDao p = new ProductDao(sessionFactoryUtils);
            p.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sessionFactoryUtils.shutdown();
        }

        SpringApplication.run(SmartktApp.class, args);
    }
}