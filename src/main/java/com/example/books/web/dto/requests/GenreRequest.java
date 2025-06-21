package com.example.books.web.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreRequest {
    @NotNull
    @Size(min = 5)
    String name;
}