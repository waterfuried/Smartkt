package ru.geekbrains.smartkt.dao;

import ru.geekbrains.smartkt.dao.users.Customer;
import ru.geekbrains.smartkt.services.hibernate.SessionFactoryUtils;

import org.hibernate.Session;

import java.util.*;

public class CustomOrderImpl implements Daocism<CustomOrder> {
    private final SessionFactoryUtils sessionFactoryUtils;

    public CustomOrderImpl(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }

    @Override public CustomOrder findById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            CustomOrder p = session.get(CustomOrder.class, id);
            session.getTransaction().commit();
            return p;
        }
    }

    @Override public List<CustomOrder> findAll() {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<CustomOrder> rl = session.createQuery("select r from CustomOrder r", CustomOrder.class)
                    .getResultList();
            session.getTransaction().commit();
            return rl;
        }
    }

    // список всех покупателей товара
    public List<Customer> findAllCustomersByProduct(Integer productId) {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<Customer> cl = session
                    .createQuery("select r.customer from CustomOrder r"+
                            " where r.product.id = :productId",
                            Customer.class)
                    .setParameter("productId", productId)
                    .getResultList();
            session.getTransaction().commit();
            return cl;
        }
    }

    // список всех купленных покупателем товаров
    public List<Product> findAllOrderedProductsByCustomer(Integer customerId) {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<Product> pl = session
                    .createQuery("select r.product from CustomOrder r"+
                            " where r.customer.id = :customerId and r.paid is not null",
                            Product.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
            session.getTransaction().commit();
            return pl;
        }
    }

    // стоимость товара (в текущий момент)
    // реализовано здесь, чтобы не создавать класс, выполняющий запросы к таблице продуктов напрямую,
    // поскольку нужен только запрос по стоимости
    public Integer findPriceByProduct(Integer productId) {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            Integer cost = session
                    .createQuery("select r.product.cost from CustomOrder r"+
                            " where r.product.id = :productId",
                            Product.class)
                    .setParameter("productId", productId)
                    .getResultList().get(0).getCost();
            session.getTransaction().commit();
            return cost;
        }
    }

    // обновить поле даты и времени оплаты заказа во всех записях заказа - либо существующим, либо текущим
    // public CustomOrder updatePaidTime() { }

    @Override public CustomOrder saveOrUpdate(CustomOrder customOrder) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(customOrder);
            CustomOrder p = session.get(CustomOrder.class, customOrder.getId());
            session.getTransaction().commit();
            return p;
        }
    }

    @Override public void deleteById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.delete(session.get(CustomOrder.class, id));
            session.getTransaction().commit();
        }
    }

    public void test() {
        CustomOrder r;
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            r = session.get(CustomOrder.class, 2);
            System.out.println("Order with id = 2: "+r);
            session.getTransaction().commit();
        }
        System.out.println("all orders = "+findAll());
        System.out.println("order with id = 3: "+findById(3));
        System.out.println("all customers ordered product with id=1: "+findAllCustomersByProduct(1));
        System.out.println("all products of customer with id=1: "+findAllOrderedProductsByCustomer(1));
    }
}