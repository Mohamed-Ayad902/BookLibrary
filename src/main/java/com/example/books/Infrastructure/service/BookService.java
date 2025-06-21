package com.example.books.Infrastructure.service;


import com.example.books.domain.repository.IBookRepo;
import com.example.books.domain.service.IBookService;
import com.example.books.web.dto.requests.BookRequest;
import com.example.books.web.dto.responses.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService implements IBookService {
    private final IBookRepo repo;

    @Autowired
    public BookService(IBookRepo repo) {
        this.repo = repo;
    }

    @Override
    public Page<BookDto> listAll(
            Integer authorId,
            Integer genreId,
            String authorName,
            String genreName,
            Pageable pageable
    ) {
        return repo.listAll(authorId, genreId, authorName, genreName, pageable);
    }

    @Override
    public BookDto getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    @Transactional
    public BookDto create(BookRequest request) {
        return repo.create(request);
    }

    @Override
    @Transactional
    public BookDto update(Integer id, BookRequest request) {
        return repo.update(id, request);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repo.delete(id);
    }
}