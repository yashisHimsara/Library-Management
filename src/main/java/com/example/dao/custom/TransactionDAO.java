package com.example.dao.custom;

import com.example.dao.CrudDAO;
import com.example.entity.CustomEntity;
import com.example.entity.Transaction;

import java.util.List;

public interface TransactionDAO extends CrudDAO<Transaction> {
    List<CustomEntity> getUserTransaction();
    List<CustomEntity> getUserAllTransaction();
    boolean updateStatus(String id);
    List<Transaction> getAllToday();
    public String getLastTransactionId();
}
