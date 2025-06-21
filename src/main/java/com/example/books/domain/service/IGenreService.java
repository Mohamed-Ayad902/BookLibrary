package com.example.books.domain.service;

import com.example.books.web.dto.requests.GenreRequest;
import com.example.books.web.dto.responses.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenreService {
    Page<GenreDto> listAll(String nameFilter, Pageable pageable);
    GenreDto getById(Integer id);
    GenreDto create(GenreRequest request);
    GenreDto update(Integer id, GenreRequest request);
    void delete(Integer id);
}