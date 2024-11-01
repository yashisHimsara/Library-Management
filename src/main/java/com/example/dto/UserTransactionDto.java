package com.example.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class UserTransactionDto {
    private String transactionId;
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private Date borrowingDate;
    private Date returnDate;
    private String status;
}
