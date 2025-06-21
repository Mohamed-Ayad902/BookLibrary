# Library Management System

A simple backend project built with Spring Boot, H2 DB, demonstrating basic CRUD API with validation, exception handling, pagination, filtering, and integration tests

## Features

- **Entities**: Book, Author, Genre  
- **CRUD APIs** with pagination & filtering  
- **Clean Architecture** layers (domain, application, infrastructure, web)  
- **Exception Handling** with `@ControllerAdvice` & custom exceptions  
- **Validation** using `@Valid`, `@NotNull`, `@Size`, etc.  
- **H2** in-memory database (switchable to PostgreSQL/MySQL)  
- **Integration Tests** with SpringBootTest & MockMvc  

## Tech Stack

- **Java** 24  
- **Spring Boot** 3.5.0  
- **H2 Database** (dev)  
- **Maven**  
- **Lombok**  

## API Endpoints

### Authors

- **List authors**  
  - **URL:** `GET /authors`  
  - **Query params:**  
    - `name` (optional): filter by partial name  
    - `page` (optional, default 0)  
    - `size` (optional, default 10)  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/authors?name=doe&page=0&size=5"
    ```
  - **Response:**  
    ```json
    {
      "content": [
        { "id": 1, "name": "John Doe", "age": 40 },
        ...
      ],
      "pageable": { ... }
    }
    ```

- **Get author by ID**  
  - **URL:** `GET /authors/{id}`  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/authors/1"
    ```
  - **Response:**  
    ```json
    { "id": 1, "name": "John Doe", "age": 40 }
    ```

- **Create author**  
  - **URL:** `POST /authors`  
  - **Body:**  
    ```json
    { "name": "Jane Austen", "age": 41 }
    ```
  - **Example:**  
    ```bash
    curl -X POST http://localhost:8080/authors \
      -H "Content-Type: application/json" \
      -d '{"name":"Jane Austen","age":41}'
    ```
  - **Response (201 Created):**  
    ```json
    { "id": 2, "name": "Jane Austen", "age": 41 }
    ```

- **Update author**  
  - **URL:** `PUT /authors/{id}`  
  - **Body:**  
    ```json
    { "name": "Jane A.", "age": 42 }
    ```
  - **Example:**  
    ```bash
    curl -X PUT http://localhost:8080/authors/2 \
      -H "Content-Type: application/json" \
      -d '{"name":"Jane A.","age":42}'
    ```
  - **Response:**  
    ```json
    { "id": 2, "name": "Jane A.", "age": 42 }
    ```

- **Delete author**  
  - **URL:** `DELETE /authors/{id}`  
  - **Example:**  
    ```bash
    curl -X DELETE http://localhost:8080/authors/2
    ```
  - **Response:** `204 No Content`

---

### Genres

- **List genres**  
  - **URL:** `GET /genres`  
  - **Query params:**  
    - `name` (optional): filter by partial name  
    - `page`, `size`  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/genres?name=fic&page=0&size=5"
    ```
  - **Response:**  
    ```json
    {
      "content": [
        { "id": 1, "name": "Science Fiction" },
        ...
      ],
      "pageable": { ... }
    }
    ```

- **Get genre by ID**  
  - **URL:** `GET /genres/{id}`  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/genres/1"
    ```
  - **Response:**  
    ```json
    { "id": 1, "name": "Science Fiction" }
    ```

- **Create genre**  
  - **URL:** `POST /genres`  
  - **Body:**  
    ```json
    { "name": "Fantasy" }
    ```
  - **Example:**  
    ```bash
    curl -X POST http://localhost:8080/genres \
      -H "Content-Type: application/json" \
      -d '{"name":"Fantasy"}'
    ```
  - **Response (201 Created):**  
    ```json
    { "id": 2, "name": "Fantasy" }
    ```

- **Update genre**  
  - **URL:** `PUT /genres/{id}`  
  - **Body:**  
    ```json
    { "name": "Sci-Fi" }
    ```
  - **Example:**  
    ```bash
    curl -X PUT http://localhost:8080/genres/1 \
      -H "Content-Type: application/json" \
      -d '{"name":"Sci-Fi"}'
    ```
  - **Response:**  
    ```json
    { "id": 1, "name": "Sci-Fi" }
    ```

- **Delete genre**  
  - **URL:** `DELETE /genres/{id}`  
  - **Example:**  
    ```bash
    curl -X DELETE http://localhost:8080/genres/1
    ```
  - **Response:** `204 No Content`

---

### Books

- **List books**  
  - **URL:** `GET /books`  
  - **Query params (all optional):**  
    - `authorId`, `genreId`  
    - `authorName`, `genreName`  
    - `page` (default 0), `size` (default 10)  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/books?authorName=Austen&page=0&size=5"
    ```
  - **Response:**  
    ```json
    {
      "content": [
        {
          "id": 1,
          "title": "Pride and Prejudice",
          "description": "...",
          "datePublished": "1813-01-28T00:00:00",
          "author": { "id": 2, "name": "Jane Austen", "age": 41 },
          "genre": { "id": 1, "name": "Romance" }
        },
        ...
      ],
      "pageable": { ... }
    }
    ```

- **Get book by ID**  
  - **URL:** `GET /books/{id}`  
  - **Example:**  
    ```bash
    curl "http://localhost:8080/books/1"
    ```
  - **Response:**  
    ```json
    {
      "id": 1,
      "title": "Pride and Prejudice",
      "description": "...",
      "datePublished": "1813-01-28T00:00:00",
      "author": { "id": 2, "name": "Jane Austen", "age": 41 },
      "genre": { "id": 1, "name": "Romance" }
    }
    ```

- **Create book**  
  - **URL:** `POST /books`  
  - **Body:**  
    ```json
    {
      "title": "Emma",
      "description": "A novel about youthful hubris.",
      "datePublished": "1815-12-23T00:00:00",
      "authorId": 2,
      "genreId": 1
    }
    ```
  - **Example:**  
    ```bash
    curl -X POST http://localhost:8080/books \
      -H "Content-Type: application/json" \
      -d '{
        "title":"Emma",
        "description":"A novel about youthful hubris.",
        "datePublished":"1815-12-23T00:00:00",
        "authorId":2,
        "genreId":1
      }'
    ```
  - **Response (201 Created):**  
    ```json
    {
      "id": 3,
      "title": "Emma",
      "description": "...",
      "datePublished": "1815-12-23T00:00:00",
      "author": { "id": 2, "name": "Jane Austen", "age": 41 },
      "genre": { "id": 1, "name": "Romance" }
    }
    ```

- **Update book**  
  - **URL:** `PUT /books/{id}`  
  - **Body:** same as **Create book**  
  - **Example:**  
    ```bash
    curl -X PUT http://localhost:8080/books/3 \
      -H "Content-Type: application/json" \
      -d '{ "title":"Emma (Revised)", ... }'
    ```
  - **Response:**  
    ```json
    {
      "id": 3,
      "title": "Emma (Revised)",
      ...
    }
    ```

- **Delete book**  
  - **URL:** `DELETE /books/{id}`  
  - **Example:**  
    ```bash
    curl -X DELETE http://localhost:8080/books/3
    ```
  - **Response:** `204 No Content`
