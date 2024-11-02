package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class Transaction {
    @Id
    private String transactionId;

    @Column(nullable = false)
    private Date borrowingDate;

    @Column(nullable = false)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name="userId" ,nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="id" , nullable = false)
    private Books books;

    @Column(nullable = false)
    private String status;
}
