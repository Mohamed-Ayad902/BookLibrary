package com.example.books.utils;

public interface IMapper<Entity, Dto> {
    Dto mapToDto(Entity entity);

    Entity mapToEntity(Dto dto);
}