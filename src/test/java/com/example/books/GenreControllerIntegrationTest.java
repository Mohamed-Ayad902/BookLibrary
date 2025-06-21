package com.example.books;


import com.example.books.Infrastructure.repository.SpringDataGenreRepository;
import com.example.books.web.dto.requests.GenreRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final SpringDataGenreRepository genreRepo;
    private final ObjectMapper mapper;

    @Autowired
    GenreControllerIntegrationTest(MockMvc mockMvc, SpringDataGenreRepository genreRepo) {
        this.mockMvc = mockMvc;
        this.genreRepo = genreRepo;
        this.mapper = new ObjectMapper();
    }

    @Test
    void createGenre_thenGetAndDelete() throws Exception {
        // create should return 201 status
        GenreRequest req = new GenreRequest();
        req.setName("Action");
        String json = mapper.writeValueAsString(req);

        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Action"));

        Integer id = genreRepo.findAll().getFirst().getId();

        // get should return 200 status
        mockMvc.perform(get("/genres/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // delete should return 204 status
        mockMvc.perform(delete("/genres/{id}", id))
                .andExpect(status().isNoContent());

        // if we try get again we should return 404 not found
        mockMvc.perform(get("/genres/{id}", id))
                .andExpect(status().isNotFound());
    }
}