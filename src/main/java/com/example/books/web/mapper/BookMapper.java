package com.example.books.web.mapper;

import com.example.books.Infrastructure.model.AuthorEntity;
import com.example.books.Infrastructure.model.BookEntity;
import com.example.books.Infrastructure.model.GenreEntity;
import com.example.books.utils.IMapper;
import com.example.books.web.dto.responses.AuthorDto;
import com.example.books.web.dto.responses.BookDto;
import com.example.books.web.dto.responses.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements IMapper<BookEntity, BookDto> {
    @Override
    public BookDto mapToDto(BookEntity e) {
        AuthorDto author = new AuthorDto(e.getAuthor().getId(), e.getAuthor().getName(), e.getAuthor().getAge());
        GenreDto genre = new GenreDto(e.getGenre().getId(), e.getGenre().getName());
        return new BookDto(e.getId(), e.getTitle(), e.getDescription(), e.getDateCreated(), author, genre);
    }

    @Override
    public BookEntity mapToEntity(BookDto d) {
        AuthorEntity author = new AuthorEntity(d.author().id(), d.author().name(), d.author().age());
        GenreEntity genre = new GenreEntity(d.genre().id(), d.genre().name());
        return new BookEntity(d.id(), d.title(), d.description(), d.datePublished(), author, genre);
    }
}