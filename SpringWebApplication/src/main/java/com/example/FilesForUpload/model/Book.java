package com.example.FilesForUpload.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false, unique = true)
    private String bookName;

    @Column(name ="book_author")
    private String bookAuthor;

    @Column(nullable = false)
    private Integer pages;

    @Column(length = 4) // Ограничение длины до 4 символов
    private String year;

    @Column(name="book_image")
    private String bookImage;

    @Column(name = "book_url")
    private String bookUrl;

}
