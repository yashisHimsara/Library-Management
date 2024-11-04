package com.example.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminHistoryTm {
    private String transactionId;
    private String userName;
    private String title;
    private String genre;
    private Date borrowDate;
    private Date returnDate;
    private String status;
    private Button remove;
}
