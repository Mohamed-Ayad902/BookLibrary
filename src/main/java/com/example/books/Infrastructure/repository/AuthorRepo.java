package com.example.books.Infrastructure.repository;

import com.example.books.Infrastructure.model.AuthorEntity;
import com.example.books.domain.repository.IAuthorRepo;
import com.example.books.exception.ResourceNotFoundException;
import com.example.books.web.dto.responses.AuthorDto;
import com.example.books.web.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepo implements IAuthorRepo {

    private final SpringDataAuthorRepository jpa;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorRepo(SpringDataAuthorRepository jpa, AuthorMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Page<AuthorDto> listAll(String nameFilter, Pageable pageable) {
        Page<AuthorEntity> page = (nameFilter != null && !nameFilter.isBlank())
                ? jpa.findByNameContainingIgnoreCase(nameFilter, pageable)
                : jpa.findAll(pageable);
        return page.map(mapper::mapToDto);
    }


    @Override
    public AuthorDto getById(Integer id) {
        AuthorEntity e = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        return mapper.mapToDto(e);
    }

    @Override
    public AuthorDto create(AuthorDto dto) {
        AuthorEntity saved = jpa.save(mapper.mapToEntity(dto));
        return mapper.mapToDto(saved);
    }

    @Override
    public AuthorDto update(Integer id, AuthorDto dto) {
        AuthorEntity existing = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        existing.setName(dto.name());
        existing.setAge(dto.age());
        AuthorEntity updated = jpa.save(existing);
        return mapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!jpa.existsById(id)) {
            throw new ResourceNotFoundException("Author", "id", id);
        }
        jpa.deleteById(id);
    }
}