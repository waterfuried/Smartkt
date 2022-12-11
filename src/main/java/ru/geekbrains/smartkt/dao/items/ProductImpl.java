package ru.geekbrains.smartkt.dao.items;

import ru.geekbrains.smartkt.dao.Daocism;
import ru.geekbrains.smartkt.services.hibernate.*;

import org.hibernate.Session;

import java.util.*;

// класс реализует логику выполнения CRUD-операций над сущностью StoredItem
public class ProductImpl implements Daocism<StoredItem> {
    private final SessionFactoryUtils sessionFactoryUtils;

    public ProductImpl(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }

    @Override
    public StoredItem findById(Integer id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            StoredItem p = session.get(StoredItem.class, id);
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public List<StoredItem> findAll() {
        try (Session session = sessionFactoryUtils.getSession()){
            session.beginTransaction();
            List<StoredItem> pl = session.createQuery("select p from StoredItem p",
                            StoredItem.class)
                    .getResultList();
            session.getTransaction().commit();
            return pl;
        }
    }

    public StoredItem findByTitle(String title) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            StoredItem p = session.createQuery("select p from StoredItem p where p.title = :title",
                            StoredItem.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return p;
        }
    }

    @Override
    public StoredItem saveOrUpdate(StoredItem item) {
        //            session.createQuery("update Item p set p.title :title where p.id = :id")
        //                    .setParameter("title", p.getTitle())
        //                    .setParameter("id", p.getId())
        //                    .executeUpdate();
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(item);
            StoredItem p = session.get(StoredItem.class, item.getId());
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
            session.delete(session.get(StoredItem.class, id));
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
        StoredItem p;
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            p = session.get(StoredItem.class, 4);
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