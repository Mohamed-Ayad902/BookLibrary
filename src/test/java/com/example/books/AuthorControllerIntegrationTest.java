package com.example.books;


import com.example.books.Infrastructure.repository.SpringDataAuthorRepository;
import com.example.books.web.dto.requests.AuthorRequest;
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
class AuthorControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final SpringDataAuthorRepository authorRepo;
    private final ObjectMapper mapper;

    @Autowired
    AuthorControllerIntegrationTest(MockMvc mockMvc, SpringDataAuthorRepository authorRepo) {
        this.mockMvc = mockMvc;
        this.authorRepo = authorRepo;
        this.mapper = new ObjectMapper();
    }

    @Test
    void createAuthor_thenGetAndDelete() throws Exception {
        // create should return 201 status
        AuthorRequest req = new AuthorRequest();
        req.setName("Mohamed Ayad");
        req.setAge(45);
        String json = mapper.writeValueAsString(req);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mohamed Ayad"))
                .andExpect(jsonPath("$.age").value(45));

        Integer id = authorRepo.findAll().getFirst().getId();

        // get should return 200 status
        mockMvc.perform(get("/authors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // delete should return 204 status
        mockMvc.perform(delete("/authors/{id}", id))
                .andExpect(status().isNoContent());

        // if we try get again we should return 404 not found
        mockMvc.perform(get("/authors/{id}", id))
                .andExpect(status().isNotFound());
    }
}