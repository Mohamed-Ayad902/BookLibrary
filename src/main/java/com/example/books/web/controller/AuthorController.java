package com.example.books.web.controller;

import com.example.books.domain.service.IAuthorService;
import com.example.books.web.dto.requests.AuthorRequest;
import com.example.books.web.dto.responses.AuthorDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final IAuthorService svc;

    @Autowired
    public AuthorController(IAuthorService svc) {
        this.svc = svc;
    }

    @GetMapping
    public Page<AuthorDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return svc.listAll(name, PageRequest.of(page, size, Sort.by("name")));
    }

    @GetMapping("/{id}")
    public AuthorDto get(@PathVariable Integer id) {
        return svc.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@Valid @RequestBody AuthorRequest req) {
        return svc.create(req);
    }

    @PutMapping("/{id}")
    public AuthorDto update(
            @PathVariable Integer id,
            @Valid @RequestBody AuthorRequest req
    ) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}