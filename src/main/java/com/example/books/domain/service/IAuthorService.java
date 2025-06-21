package com.example.books.domain.service;

import com.example.books.web.dto.requests.AuthorRequest;
import com.example.books.web.dto.responses.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorService {
    Page<AuthorDto> listAll(String nameFilter, Pageable pageable);
    AuthorDto getById(Integer id);
    AuthorDto create(AuthorRequest request);
    AuthorDto update(Integer id, AuthorRequest request);
    void delete(Integer id);
}