package com.example.dao.custom.impl;

import com.example.config.FactoryConfiguration;
import com.example.dao.custom.UserDAO;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean add(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<User> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query<User> query = session.createQuery("FROM User", User.class);
        List<User> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean isExists(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user= session.get(User.class,id);
        transaction.commit();
        session.close();

        return user != null;
    }

    @Override
    public User search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE id=:id",User.class);
        query.setParameter("id",id);

        User user = query.getSingleResult();

        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user=session.get(User.class,id);

        if (user!=null){
            session.delete(user);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    public String getLastUserId(){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<String> query = session.createQuery(
                "SELECT u.userId FROM User u ORDER BY u.userId DESC", String.class
        );
        query.setMaxResults(1);
        String latestUserId = query.uniqueResult();
        return latestUserId;
    }
}
