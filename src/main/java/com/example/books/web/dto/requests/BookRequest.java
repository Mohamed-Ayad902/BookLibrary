package com.example.books.web.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRequest {
    @NotNull
    @Size(min = 10)
    private String title;
    @NotNull
    @Size(min = 15)
    private String description;
    @NotNull
    LocalDateTime datePublished;
    @NotNull
    private Integer authorId;
    @NotNull
    private Integer genreId;
}