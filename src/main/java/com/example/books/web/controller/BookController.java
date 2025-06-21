package com.example.books.web.controller;

import com.example.books.domain.service.IBookService;
import com.example.books.web.dto.requests.BookRequest;
import com.example.books.web.dto.responses.BookDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final IBookService svc;

    @Autowired
    public BookController(IBookService svc) {
        this.svc = svc;
    }

    @GetMapping
    public Page<BookDto> list(
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String genreName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pg = PageRequest.of(page, size, Sort.by("title"));
        return svc.listAll(authorId, genreId, authorName, genreName, pg);
    }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable Integer id) {
        return svc.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@Valid @RequestBody BookRequest req) {
        return svc.create(req);
    }

    @PutMapping("/{id}")
    public BookDto update(
            @PathVariable Integer id,
            @Valid @RequestBody BookRequest req
    ) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
