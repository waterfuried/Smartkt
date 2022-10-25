package ru.geekbrains.smartkt.dao;

import ru.geekbrains.smartkt.services.hibernate.*;

import org.hibernate.Session;

import java.util.*;

// класс реализует логику выполнения CRUD-операций над сущностью Product
public class ProductDao implements Daocism<Product> {
    private final SessionFactoryUtils sessionFactoryUtils;

    public ProductDao(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }

    @Override
    public Product findById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Product p = session.get(Product.class, id);
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public List<Product> findAll() {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<Product> pl = session.createQuery("select p from Product p").getResultList();
            session.getTransaction().commit();
            return pl;
        }
    }

    @Override
    public Product findByTitle(String title) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Product p = session.createQuery("select p from Product p where p.title = :title", Product.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public Product saveOrUpdate(Product product) {
        //            session.createQuery("update Product p set p.title :title where p.id = :id")
        //                    .setParameter("title", p.getTitle())
        //                    .setParameter("id", p.getId())
        //                    .executeUpdate();
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            Product p = session.get(Product.class, product.getId());
            //p.setTitle(product.getTitle());
            //p.setCost(product.getCost());
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.delete(session.get(Product.class, id));
            session.getTransaction().commit();
        }
    }

    // перед запуском SpringBoot-приложения
    // метод выполнит проверку реализованных методов работы с БД
    public void test() {
        Product p;
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            // TODO: p всегда null после замены интерфейса на параметризованный
            p = session.get(Product.class, 4);
            System.out.println("product with id = 4: "+p);
            session.getTransaction().commit();
        }
        System.out.println("all products = "+findAll());
        System.out.println("product with id = 2: "+findById(2));
        deleteById(2);
        p = findById(3);
        p.setCost(29);
        saveOrUpdate(p);
        System.out.println("all products = "+findAll());
    }
}