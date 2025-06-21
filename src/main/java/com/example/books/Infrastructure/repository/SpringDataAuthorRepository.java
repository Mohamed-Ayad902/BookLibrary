package com.example.books.Infrastructure.repository;

import com.example.books.Infrastructure.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SpringDataAuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    Page<AuthorEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}