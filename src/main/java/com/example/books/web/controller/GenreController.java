package com.example.books.web.controller;

import com.example.books.domain.service.IGenreService;
import com.example.books.web.dto.requests.GenreRequest;
import com.example.books.web.dto.responses.GenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final IGenreService svc;

    @Autowired
    public GenreController(IGenreService svc) {
        this.svc = svc;
    }

    @GetMapping
    public Page<GenreDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return svc.listAll(name, PageRequest.of(page, size, Sort.by("name")));
    }

    @GetMapping("/{id}")
    public GenreDto get(@PathVariable Integer id) {
        return svc.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto create(@Valid @RequestBody GenreRequest req) {
        return svc.create(req);
    }

    @PutMapping("/{id}")
    public GenreDto update(
            @PathVariable Integer id,
            @Valid @RequestBody GenreRequest req
    ) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}