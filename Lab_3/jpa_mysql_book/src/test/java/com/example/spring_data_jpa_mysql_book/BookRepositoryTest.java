package com.example.spring_data_jpa_mysql_book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        assertThat(savedBook.getAuthor()).isEqualTo("Test Author");
    }

    @Test
    public void testFindBookById() {
        // Save a book to the database
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findById(book.getId());

        assertThat(optionalBook).isPresent();
        Book foundBook = optionalBook.get();
        assertThat(foundBook.getTitle()).isEqualTo("Test Book");
        assertThat(foundBook.getAuthor()).isEqualTo("Test Author");
    }

    @Test
    public void testUpdateBook() {
        
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        book.setTitle("Updated Title");
        bookRepository.save(book);

       
        Book updatedBook = bookRepository.findById(book.getId()).orElse(null);

        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        bookRepository.delete(book);

        assertThat(bookRepository.findById(book.getId())).isEmpty();
    }
}
