package com.example.bo.custom.impl;

import com.example.bo.custom.TransactionBO;
import com.example.dao.DAOFactory;
import com.example.dao.custom.TransactionDAO;
import com.example.dto.TransactionDto;
import com.example.dto.UserTransactionDto;
import com.example.entity.CustomEntity;
import com.example.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionBOImpl implements TransactionBO {

    TransactionDAO transactionDAO= (TransactionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);


    @Override
    public boolean saveTransaction(TransactionDto dto) {
        return transactionDAO.add(new Transaction(dto.getTransactionId(),dto.getBorrowingDate(),dto.getReturnDate()
        ,dto.getUser(),dto.getBook(), dto.getStatus()));
    }

    @Override
    public List<TransactionDto> getTransactions() {
        List<Transaction> all = transactionDAO.getAll();

        List<TransactionDto> allTransaction = new ArrayList<>();

        for (Transaction transaction : all) {
            allTransaction.add(new TransactionDto(transaction.getTransactionId(),transaction.getBorrowingDate(),transaction.getReturnDate(),
                    transaction.getUser(),transaction.getBooks(),transaction.getStatus()));
        }
        return allTransaction;
    }

    @Override
    public List<UserTransactionDto> getUserTransactions() {
        List<CustomEntity> all = transactionDAO.getUserTransaction();

        List<UserTransactionDto> allTransaction = new ArrayList<>();

        for ( CustomEntity transaction : all) {
            allTransaction.add(new UserTransactionDto(transaction.getTransactionId(), transaction.getBookId(),transaction.getTitle(),transaction.getAuthor(), transaction.getGenre(),
                    transaction.getBorrowingDate(),transaction.getReturnDate(),transaction.getStatus()));
        }
        return allTransaction;
    }

    @Override
    public List<UserTransactionDto> getUserAllTransaction() {
        List<CustomEntity> all = transactionDAO.getUserAllTransaction();

        List<UserTransactionDto> allTransaction = new ArrayList<>();

        for ( CustomEntity transaction : all) {
            allTransaction.add(new UserTransactionDto(transaction.getTransactionId(), transaction.getBookId(),transaction.getTitle(),transaction.getAuthor(), transaction.getGenre(),
                    transaction.getBorrowingDate(),transaction.getReturnDate(),transaction.getStatus()));
        }
        return allTransaction;
    }

    @Override
    public boolean updateStatus(String id) {
        return transactionDAO.updateStatus(id);
    }

    @Override
    public boolean delete(String id) {
        return transactionDAO.delete(id);
    }

    @Override
    public List<Transaction> getAllToday() {
        return transactionDAO.getAllToday();
    }

    @Override
    public String getLastTransactionId() {
        return transactionDAO.getLastTransactionId();
    }
}
