package com.example.books.Infrastructure.repository;

import com.example.books.Infrastructure.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataBookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findByAuthorId(Integer authorId, Pageable pageable);

    Page<BookEntity> findByAuthor_NameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<BookEntity> findByGenreId(Integer genreId, Pageable pageable);

    Page<BookEntity> findByGenre_NameContainingIgnoreCase(String genreName, Pageable pageable);
}