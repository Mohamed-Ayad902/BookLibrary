package com.example.books.domain.repository;

import com.example.books.web.dto.responses.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorRepo {
    Page<AuthorDto> listAll(String nameFilter, Pageable pageable);
    AuthorDto getById(Integer id);
    AuthorDto create(AuthorDto dto);
    AuthorDto update(Integer id, AuthorDto dto);
    void delete(Integer id);
}