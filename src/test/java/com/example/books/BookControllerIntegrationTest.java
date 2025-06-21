package com.example.books;

import com.example.books.Infrastructure.model.AuthorEntity;
import com.example.books.Infrastructure.model.GenreEntity;
import com.example.books.Infrastructure.repository.SpringDataAuthorRepository;
import com.example.books.Infrastructure.repository.SpringDataBookRepository;
import com.example.books.Infrastructure.repository.SpringDataGenreRepository;
import com.example.books.web.dto.requests.BookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final SpringDataAuthorRepository authorRepo;
    private final SpringDataGenreRepository genreRepo;
    private final SpringDataBookRepository bookRepo;
    private final ObjectMapper mapper;

    private Integer authorId;
    private Integer genreId;

    @Autowired
    BookControllerIntegrationTest(MockMvc mockMvc, SpringDataAuthorRepository authorRepo, SpringDataGenreRepository genreRepo, SpringDataBookRepository bookRepo, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
        this.mapper = mapper;
    }

    // to ensure that any data left over from a previous test
    // and create author, genre as we need them below to create a book
    @BeforeEach
    void setup() {
        authorRepo.deleteAll();
        genreRepo.deleteAll();
        bookRepo.deleteAll();
        AuthorEntity a = authorRepo.save(AuthorEntity.builder().name("Ronaldo").age(41).build());
        GenreEntity g = genreRepo.save(GenreEntity.builder().name("Football").build());
        authorId = a.getId();
        genreId = g.getId();
    }

    @Test
    void createGetUpdateDeleteBook_flow() throws Exception {
        // create should return 201 status
        BookRequest req = new BookRequest();
        req.setTitle("The book title");
        req.setDescription("A novel about youthful hubris.");
        req.setDatePublished(LocalDateTime.of(1815, 12, 23, 0, 0));
        req.setAuthorId(authorId);
        req.setGenreId(genreId);
        String json = mapper.writeValueAsString(req);

        String created = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The book title"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer bookId = mapper.readTree(created).get("id").asInt();

        // get should return 200 status
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId));

        // update should return 200 status
        req.setTitle("The UPDATED book title");
        String updJson = mapper.writeValueAsString(req);
        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The UPDATED book title"));

        // delete should return 204 status
        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());

        // if we try get again we should return 404 not found
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }
}