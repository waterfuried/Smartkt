package ru.geekbrains.smartkt.dao.items;

import ru.geekbrains.smartkt.dao.Daocism;
import ru.geekbrains.smartkt.services.hibernate.*;

import org.hibernate.Session;

import java.util.*;

// класс реализует логику выполнения CRUD-операций над сущностью Item
public class ProductImpl implements Daocism<Item> {
    private final SessionFactoryUtils sessionFactoryUtils;

    public ProductImpl(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }

    @Override
    public Item findById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Item p = session.get(Item.class, id);
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public List<Item> findAll() {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<Item> pl = session.createQuery("select p from Item p",
                            Item.class)
                    .getResultList();
            session.getTransaction().commit();
            return pl;
        }
    }

    public Item findByTitle(String title) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Item p = session.createQuery("select p from Item p where p.title = :title",
                            Item.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public Item saveOrUpdate(Item item) {
        //            session.createQuery("update Item p set p.title :title where p.id = :id")
        //                    .setParameter("title", p.getTitle())
        //                    .setParameter("id", p.getId())
        //                    .executeUpdate();
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(item);
            Item p = session.get(Item.class, item.getId());
            //p.setTitle(item.getTitle());
            //p.setCost(item.getCost());
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.delete(session.get(Item.class, id));
            //удаление запросом к БД происходит быстрее,
            //чем пометка объекта на удаление и его выполнение
            //session.createQuery("delete from Product p where p.id = :id")
            //        .setParameter("id", id)
            //        .executeUpdate();
            session.getTransaction().commit();
        }
    }

    /*
        проверка реализованных методов работы с БД

        любое обращение к БД каким-либо методом (особенно это важно при обновлении ее таблиц)
        выполняется в рамках отдельной транзакции, на создание которой затрачиваются, в общем,
        определенные ресурсы - память, процессорное время и т.п.
        если есть некая группа последовательных операций с БД, эту группу стоит выделить
        в отдельный метод и пометить его аннотацией @Transactional - в этом случае уже вся
        группа операций будет выполняться в рамках одной транзакции
    */
    public void test() {
        Item p;
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            p = session.get(Item.class, 4);
            System.out.println("item with id = 4: "+p);
            session.getTransaction().commit();
        }
        System.out.println("all items = "+findAll());
        System.out.println("item with id = 2: "+findById(2));
        deleteById(2);
        p = findById(3);
        p.setCost(29);
        saveOrUpdate(p);
        System.out.println("all items = "+findAll());
    }
}