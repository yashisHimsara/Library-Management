package com.example.bo.custom;

import com.example.bo.SuperBO;
import com.example.dto.TransactionDto;
import com.example.dto.UserTransactionDto;
import com.example.entity.Transaction;

import java.util.List;

public interface TransactionBO extends SuperBO {
    boolean saveTransaction(TransactionDto dto);
    List<TransactionDto> getTransactions();
    List<UserTransactionDto> getUserTransactions();
    public List<UserTransactionDto> getUserAllTransaction();
    public boolean updateStatus(String id);
    public boolean delete(String id);
    List<Transaction> getAllToday();
    public String getLastTransactionId();

}
