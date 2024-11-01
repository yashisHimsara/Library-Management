package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BooksDTO {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean availability;
}
