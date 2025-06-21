package com.example.books.Infrastructure.service;

import com.example.books.domain.repository.IGenreRepo;
import com.example.books.domain.service.IGenreService;
import com.example.books.web.dto.requests.GenreRequest;
import com.example.books.web.dto.responses.GenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenreService implements IGenreService {

    private final IGenreRepo repo;

    @Autowired
    public GenreService(IGenreRepo repo) {
        this.repo = repo;
    }

    @Override
    public Page<GenreDto> listAll(String nameFilter, Pageable pageable) {
        return repo.listAll(nameFilter, pageable);
    }

    @Override
    public GenreDto getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    @Transactional
    public GenreDto create(GenreRequest request) {
        GenreDto dto = new GenreDto(null, request.getName());
        return repo.create(dto);
    }

    @Override
    @Transactional
    public GenreDto update(Integer id, GenreRequest request) {
        GenreDto dto = new GenreDto(id, request.getName());
        return repo.update(id, dto);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repo.delete(id);
    }
}