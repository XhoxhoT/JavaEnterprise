package com.example.spring_data_jpa_mysql_book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository repository;

    @Test
    public void testGetBookWithId() throws Exception {
        Book book = new Book(1L, "Book 1", "Author 1");
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"));
    }

    @Test
    public void testFindBookWithName() throws Exception {
        Book book1 = new Book(1L, "Book 1", "Author 1");
        Book book2 = new Book(2L, "Book 2", "Author 1");
        Mockito.when(repository.findByAuthor("Author 1")).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/books?author=Author 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].author").value("Author 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Book 2"))
                .andExpect(jsonPath("$[1].author").value("Author 1"));
    }

    @Test
    public void testUpdateBookFromDB() throws Exception {
        Book book = new Book(1L, "Book 1", "Author 1");
        Book updatedBook = new Book(1L, "Updated Book 1", "Updated Author 1");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(repository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Book 1\", \"author\": \"Updated Author 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Book 1"))
                .andExpect(jsonPath("$.author").value("Updated Author 1"));
    }

    @Test
    public void testDeleteBookWithId() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());
    }
}
