package com.example.books.web.mapper;

import com.example.books.Infrastructure.model.GenreEntity;
import com.example.books.utils.IMapper;
import com.example.books.web.dto.responses.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper implements IMapper<GenreEntity, GenreDto> {
    @Override
    public GenreDto mapToDto(GenreEntity e) {
        return new GenreDto(e.getId(), e.getName());
    }

    @Override
    public GenreEntity mapToEntity(GenreDto d) {
        return GenreEntity.builder()
                .id(d.id())
                .name(d.name())
                .build();
    }
}