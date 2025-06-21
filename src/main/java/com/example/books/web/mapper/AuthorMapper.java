package com.example.books.web.mapper;

import com.example.books.Infrastructure.model.AuthorEntity;
import com.example.books.utils.IMapper;
import com.example.books.web.dto.responses.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements IMapper<AuthorEntity, AuthorDto> {
    @Override
    public AuthorDto mapToDto(AuthorEntity e) {
        return new AuthorDto(e.getId(), e.getName(), e.getAge());
    }

    @Override
    public AuthorEntity mapToEntity(AuthorDto d) {
        return AuthorEntity.builder()
                .id(d.id())
                .name(d.name())
                .age(d.age())
                .build();
    }
}