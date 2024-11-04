package com.example.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowedBookTm {
    private String transactionId;
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private Date borrowDate;
    private Date returnDate;
    private Button returnBook;
}
