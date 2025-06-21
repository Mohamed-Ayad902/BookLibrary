package com.example.books.web.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotNull
    @Size(min = 3)
    String name;
    @NotNull
    @Min(14)
    Integer age;
}