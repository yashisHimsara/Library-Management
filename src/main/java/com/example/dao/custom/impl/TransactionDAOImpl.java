package com.example.dao.custom.impl;

import com.example.config.FactoryConfiguration;
import com.example.controller.UserLoginFormController;
import com.example.dao.custom.TransactionDAO;
import com.example.entity.CustomEntity;
import com.example.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public boolean add(Transaction entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Transaction> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();

        Query<Transaction> query = session.createQuery("FROM Transaction ", Transaction.class);
        List<Transaction> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public List<Transaction> getAllToday() {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();

        LocalDate date = LocalDate.now();

        Query<Transaction> query = session.createQuery("FROM Transaction WHERE borrowingDate= : date", Transaction.class);
        query.setParameter("date", date);

        List<Transaction> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(Transaction entity) {
        return false;
    }

    @Override
    public boolean updateStatus(String id) {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("UPDATE Transaction SET status = : status WHERE transactionId = :transactionId");
        query.setParameter("status","Returned");
        query.setParameter("transactionId", id);
        int rowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return rowsUpdated>0;
    }

    @Override
    public boolean isExists(String id) {
        return false;
    }

    @Override
    public Transaction search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Transaction transaction1 = session.get(Transaction.class, id);

        if (transaction1!=null){
            session.delete(transaction1);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    public List<CustomEntity> getUserTransaction() {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Query<Object[]> query = session.createQuery(
                "SELECT t.transactionId, t.books.id, t.books.title,t.books.author, t.books.genre, t.borrowingDate, t.returnDate, t.status " +
                        "FROM Transaction t " +
                        "WHERE t.user.userId = :userId AND t.status = :status",
                Object[].class
        ).setParameter("userId", UserLoginFormController.userId)
         .setParameter("status", "To return");;

        List<Object[]> resultList = query.getResultList();
        List<CustomEntity> customEntities = new ArrayList<>();

        for (Object[] result : resultList) {
            String transactionId = (String) result[0];
            String bookId = (String) result[1];
            String title = (String) result[2];
            String author= (String) result[3];
            String genre = (String) result[4];
            Date borrowingDate = (Date) result[5];
            Date returnDate = (Date) result[6];
            String status = (String) result[7];

            CustomEntity customEntity = new CustomEntity(transactionId, bookId, title, author,genre, borrowingDate, returnDate, status);
            customEntities.add(customEntity);
        }

        transaction.commit();
        session.close();
        return customEntities;
    }

    public List<CustomEntity> getUserAllTransaction() {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Query<Object[]> query = session.createQuery(
                        "SELECT t.transactionId, t.books.id, t.books.title,t.books.author, t.books.genre, t.borrowingDate, t.returnDate, t.status " +
                                "FROM Transaction t " +
                                "WHERE t.user.userId = :userId",
                        Object[].class
                ).setParameter("userId", UserLoginFormController.userId);

        List<Object[]> resultList = query.getResultList();
        List<CustomEntity> customEntities = new ArrayList<>();

        for (Object[] result : resultList) {
            String transactionId = (String) result[0];
            String bookId = (String) result[1];
            String title = (String) result[2];
            String author= (String) result[3];
            String genre = (String) result[4];
            Date borrowingDate = (Date) result[5];
            Date returnDate = (Date) result[6];
            String status = (String) result[7];

            CustomEntity customEntity = new CustomEntity(transactionId, bookId, title, author,genre, borrowingDate, returnDate, status);
            customEntities.add(customEntity);
        }

        transaction.commit();
        session.close();
        return customEntities;
    }

    public String getLastTransactionId(){
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Query<String> query = session.createQuery(
                "SELECT t.transactionId FROM Transaction t ORDER BY t.transactionId DESC", String.class
        );
        query.setMaxResults(1);
        String latestTransactionId = query.uniqueResult();
        return latestTransactionId;
    }
}
