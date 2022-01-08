package com.yhkim.hello.dto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;
    @Column(name ="title")
    private String title;
    @Column(name ="author")
    private String author;
    @Column(name ="contents")
    private String contents;
    @Column
    private LocalDate created_date;

    @PrePersist
    public void createdAt() {
        this.created_date = LocalDate.now();
    }
}
