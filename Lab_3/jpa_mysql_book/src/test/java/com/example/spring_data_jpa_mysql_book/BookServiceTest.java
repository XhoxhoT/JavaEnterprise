package com.example.spring_data_jpa_mysql_book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Book 1", "Author 1"));
        mockBooks.add(new Book(2L, "Book 2", "Author 2"));
        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
    }

    @Test
    public void testGetBookById() {
     
        long id = 1L;
        Book mockBook = new Book(id, "Book 1", "Author 1");
        when(bookRepository.findById(id)).thenReturn(Optional.of(mockBook));

        Optional<Book> result = bookService.getBookById(id);

        assertTrue(result.isPresent());
        assertEquals("Book 1", result.get().getTitle());
        assertEquals("Author 1", result.get().getAuthor());
    }
    @Test
    public void testCreateBook() {
        
        Book book = new Book("Book Title", "Book Author");
        Book savedBook = new Book(1L, "Book Title", "Book Author");
        when(bookRepository.save(book)).thenReturn(savedBook);

        Book result = bookService.createBook(book);

        assertNotNull(result);
        assertEquals(savedBook.getId(), result.getId());
        assertEquals(savedBook.getTitle(), result.getTitle());
        assertEquals(savedBook.getAuthor(), result.getAuthor());
    }

    @Test
    public void testUpdateBook() {
       
        long id = 1L;
        Book existingBook = new Book(id, "Existing Book Title", "Existing Book Author");
        Book updatedBook = new Book(id, "Updated Book Title", "Updated Book Author");
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(id, updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook.getId(), result.getId());
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
    }

    @Test
    public void testDeleteBook() {
   
        long id = 1L;

        assertDoesNotThrow(() -> bookService.deleteBook(id));

        verify(bookRepository, times(1)).deleteById(id);
    }



}


