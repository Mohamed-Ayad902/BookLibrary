package com.example.books.Infrastructure.repository;
import com.example.books.Infrastructure.model.AuthorEntity;
import com.example.books.Infrastructure.model.BookEntity;
import com.example.books.Infrastructure.model.GenreEntity;
import com.example.books.domain.repository.IBookRepo;
import com.example.books.exception.ResourceNotFoundException;
import com.example.books.web.dto.requests.BookRequest;
import com.example.books.web.dto.responses.BookDto;
import com.example.books.web.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepo implements IBookRepo {

    private final SpringDataBookRepository jpa;
    private final SpringDataAuthorRepository authorJpa;
    private final SpringDataGenreRepository genreJpa;
    private final BookMapper mapper;

    @Autowired
    public BookRepo(
            SpringDataBookRepository jpa,
            SpringDataAuthorRepository authorJpa,
            SpringDataGenreRepository genreJpa,
            BookMapper mapper
    ) {
        this.jpa = jpa;
        this.authorJpa = authorJpa;
        this.genreJpa = genreJpa;
        this.mapper = mapper;
    }

    @Override
    public Page<BookDto> listAll(
            Integer authorId,
            Integer genreId,
            String authorName,
            String genreName,
            Pageable pageable
    ) {
        Page<BookEntity> page;

        if (authorId != null) {
            page = jpa.findByAuthorId(authorId, pageable);
        } else if (authorName != null && !authorName.isBlank()) {
            page = jpa.findByAuthor_NameContainingIgnoreCase(authorName, pageable);
        } else if (genreId != null) {
            page = jpa.findByGenreId(genreId, pageable);
        } else if (genreName != null && !genreName.isBlank()) {
            page = jpa.findByGenre_NameContainingIgnoreCase(genreName, pageable);
        } else {
            page = jpa.findAll(pageable);
        }

        return page.map(mapper::mapToDto);
    }

    @Override
    public BookDto getById(Integer id) {
        BookEntity e = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book","id",id));
        return mapper.mapToDto(e);
    }

    @Override
    public BookDto create(BookRequest req) {
        AuthorEntity author = authorJpa.findById(req.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author","id",req.getAuthorId()));
        GenreEntity genre = genreJpa.findById(req.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre","id",req.getGenreId()));

        BookEntity e = BookEntity.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dateCreated(req.getDatePublished())
                .author(author)
                .genre(genre)
                .build();

        BookEntity saved = jpa.save(e);
        return mapper.mapToDto(saved);
    }

    @Override
    public BookDto update(Integer id, BookRequest req) {
        BookEntity existing = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book","id",id));

        existing.setTitle(req.getTitle());
        existing.setDescription(req.getDescription());
        existing.setDateCreated(req.getDatePublished());

        // only change relationships if provided
        if (!existing.getAuthor().getId().equals(req.getAuthorId())) {
            AuthorEntity a = authorJpa.findById(req.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author","id",req.getAuthorId()));
            existing.setAuthor(a);
        }
        if (!existing.getGenre().getId().equals(req.getGenreId())) {
            GenreEntity g = genreJpa.findById(req.getGenreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Genre","id",req.getGenreId()));
            existing.setGenre(g);
        }

        BookEntity updated = jpa.save(existing);
        return mapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!jpa.existsById(id)) {
            throw new ResourceNotFoundException("Book","id",id);
        }
        jpa.deleteById(id);
    }
}
