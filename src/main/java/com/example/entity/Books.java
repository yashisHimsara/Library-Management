package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Books {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private boolean availability;

    @OneToMany(mappedBy = "books")
    private List<Transaction> transactions;

    public Books(String bookId, String title, String author, String genre, boolean availability) {
        this.id = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }
}
