package com.example.books.domain.service;

import com.example.books.web.dto.requests.BookRequest;
import com.example.books.web.dto.responses.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Page<BookDto> listAll(
            Integer authorId,
            Integer genreId,
            String authorName,
            String genreName,
            Pageable pageable
    );
    BookDto getById(Integer id);
    BookDto create(BookRequest request);
    BookDto update(Integer id, BookRequest request);
    void delete(Integer id);
}
