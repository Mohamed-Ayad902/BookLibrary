package com.example.books.Infrastructure.repository;

import com.example.books.Infrastructure.model.GenreEntity;
import com.example.books.domain.repository.IGenreRepo;
import com.example.books.exception.ResourceNotFoundException;
import com.example.books.web.dto.responses.GenreDto;
import com.example.books.web.mapper.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepo implements IGenreRepo {

    private final SpringDataGenreRepository jpa;
    private final GenreMapper mapper;

    @Autowired
    public GenreRepo(SpringDataGenreRepository jpa, GenreMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Page<GenreDto> listAll(String nameFilter, Pageable pageable) {
        Page<GenreEntity> page = (nameFilter != null && !nameFilter.isBlank())
                ? jpa.findByNameContainingIgnoreCase(nameFilter, pageable)
                : jpa.findAll(pageable);
        return page.map(mapper::mapToDto);
    }

    @Override
    public GenreDto getById(Integer id) {
        GenreEntity e = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre","id",id));
        return mapper.mapToDto(e);
    }

    @Override
    public GenreDto create(GenreDto dto) {
        GenreEntity saved = jpa.save(mapper.mapToEntity(dto));
        return mapper.mapToDto(saved);
    }

    @Override
    public GenreDto update(Integer id, GenreDto dto) {
        GenreEntity existing = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre","id",id));
        existing.setName(dto.name());
        GenreEntity updated = jpa.save(existing);
        return mapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!jpa.existsById(id)) {
            throw new ResourceNotFoundException("Genre","id",id);
        }
        jpa.deleteById(id);
    }
}