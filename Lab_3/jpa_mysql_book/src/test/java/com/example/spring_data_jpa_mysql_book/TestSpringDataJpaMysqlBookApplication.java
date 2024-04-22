package com.example.spring_data_jpa_mysql_book;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class TestSpringDataJpaMysqlBookApplication {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;

	@LocalServerPort
	private int port;

	@Container
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.1")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	@BeforeAll
	static void setUp() {
		mySQLContainer.start();
	}

	@AfterAll
	static void tearDown() {
		mySQLContainer.stop();
	}

	@BeforeEach
	void init() {
		restTemplate
				= new TestRestTemplate("user", "password");
	}

	@Test
	public void testAddBook() {
		Book book = new Book("Book Test", "Author Test");
		ResponseEntity<Book> responseEntity = restTemplate.postForEntity(getBaseUrl(), book, Book.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Book savedBook = responseEntity.getBody();
		assertNotNull(savedBook);
		assertEquals(book.getTitle(), savedBook.getTitle());
		assertEquals(book.getAuthor(), savedBook.getAuthor());

		bookRepository.deleteById(savedBook.getId());
	}

	private String getBaseUrl() {
		return "http://localhost:" + port + "/books";
	}
}

