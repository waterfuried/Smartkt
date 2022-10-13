package ru.geekbrains.smartkt.services.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class SessionFactoryUtils {
    private SessionFactory factory;

    public void init(String cfg) { factory = new Configuration().configure(cfg).buildSessionFactory(); }

    public Session getSession() { return factory.getCurrentSession(); }

    public void shutdown() { if (factory != null) factory.close(); }
}