package com.example.books.Infrastructure.service;

import com.example.books.domain.repository.IAuthorRepo;
import com.example.books.domain.service.IAuthorService;
import com.example.books.web.dto.requests.AuthorRequest;
import com.example.books.web.dto.responses.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService implements IAuthorService {

    private final IAuthorRepo repo;

    @Autowired
    public AuthorService(IAuthorRepo repo) {
        this.repo = repo;
    }

    @Override
    public Page<AuthorDto> listAll(String name, Pageable pageable) {
        return repo.listAll(name, pageable);
    }

    @Override
    public AuthorDto getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorRequest request) {
        // here we map request to dto
        AuthorDto dto = new AuthorDto(null, request.getName(), request.getAge());
        return repo.create(dto);
    }

    @Override
    @Transactional
    public AuthorDto update(Integer id, AuthorRequest request) {
        AuthorDto dto = new AuthorDto(id, request.getName(), request.getAge());
        return repo.update(id, dto);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repo.delete(id);
    }
}