package com.example.books.domain.repository;

import com.example.books.web.dto.responses.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenreRepo {
    Page<GenreDto> listAll(String nameFilter, Pageable pageable);
    GenreDto getById(Integer id);
    GenreDto create(GenreDto dto);
    GenreDto update(Integer id, GenreDto dto);
    void delete(Integer id);
}