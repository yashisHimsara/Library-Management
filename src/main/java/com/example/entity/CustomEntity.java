package com.example.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomEntity {
    private String transactionId;
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private Date borrowingDate;
    private Date returnDate;
    private String status;
}
