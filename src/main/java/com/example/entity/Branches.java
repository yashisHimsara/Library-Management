package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Branches {
    @Id
    private String code;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String contactNumber;
    @Column(nullable = false)
    private String status;
}
