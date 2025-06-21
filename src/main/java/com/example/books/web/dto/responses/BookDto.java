package com.example.books.web.dto.responses;

import java.time.LocalDateTime;

public record BookDto(
        Integer id,
        String title,
        String description,
        LocalDateTime datePublished,
        AuthorDto author,
        GenreDto genre
) {
}
