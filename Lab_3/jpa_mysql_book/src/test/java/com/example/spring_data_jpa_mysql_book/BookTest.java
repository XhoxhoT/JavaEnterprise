package com.example.spring_data_jpa_mysql_book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BookTest {

    @Test
    public void testConstructorWithId() {
  
        Long id = 1L;
        String title = "Test Title";
        String author = "Test Author";
    
        Book book = new Book(id, title, author);
        
        // Assert
        assertEquals(id, book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    public void testConstructorWithoutId() {
  
        String title = "Test Title";
        String author = "Test Author";
        
        Book book = new Book(title, author);
    
        assertEquals(null, book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    public void testToString() {

        Long id = 1L;
        String title = "Test Title";
        String author = "Test Author";
        Book book = new Book(id, title, author);
     
        String toStringResult = book.toString();
        
        String expectedToString = "Book{id=1, title='Test Title', author='Test Author'}";
        assertEquals(expectedToString, toStringResult);
    }
}
