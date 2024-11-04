package com.example.tm;

import javafx.scene.control.Button;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BooksTm {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private String availability;
    private Button remove;
}
