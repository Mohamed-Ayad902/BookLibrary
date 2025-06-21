package com.example.books.Infrastructure.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
}