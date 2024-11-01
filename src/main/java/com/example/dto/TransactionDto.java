package com.example.dto;

import lombok.*;
import org.example.entity.Books;
import org.example.entity.User;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TransactionDto {
    private String transactionId;
    private Date borrowingDate;
    private Date returnDate;
    private User user;
    private Books book;
    private String status;
}
