package com.example.books.Infrastructure.repository;

import com.example.books.Infrastructure.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SpringDataGenreRepository extends JpaRepository<GenreEntity, Integer> {
    Page<GenreEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}