/**
 * 
 */
package com.smartdubai.bookstore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.exception.BookNotFoundException;
import com.smartdubai.bookstore.service.BookService;

/**
 * @author abhinav
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BookService service;

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#listAllBooks()}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testListAllBooks() throws Exception {
		Book theBook = new Book();
		theBook.setName("Test");
		List<Book> allBooks = Arrays.asList(theBook);

		Mockito.when(service.fetchAllBooks()).thenReturn(allBooks);

		mvc.perform(get("/api/v1/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1))).andExpect(jsonPath("$[0].name", is(theBook.getName())));
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#listBookById(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testListBookById() throws Exception {
		Book theBook = new Book();
		theBook.setId(1L);
		theBook.setName("Test");

		Mockito.when(service.fetchBookById(1L)).thenReturn(theBook);

		mvc.perform(get("/api/v1/books/" + 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("name", is(theBook.getName())));

	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#testListBookById_ForInvalidBookId(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testListBookById_ForInvalidBookId() throws Exception {

		Mockito.doThrow(new BookNotFoundException("Book Not Found, Invalid Book ID")).when(service).fetchBookById(11L);

		mvc.perform(get("/api/v1/books/" + 11).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#addBook(com.smartdubai.bookstore.entity.Book)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddBook() throws Exception {
		Book theBook = new Book();
		theBook.setId(1l);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(1d);

		Mockito.when(service.saveOrUpdate(Mockito.any(Book.class))).thenReturn(theBook);
		mvc.perform(post("/api/v1/books").contentType(MediaType.APPLICATION_JSON).content(toJson(theBook)))
				.andExpect(status().isCreated());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#updateBook(com.smartdubai.bookstore.entity.Book)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateBook() throws Exception {
		Book theBook = new Book();
		theBook.setId(1L);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(1d);

		Mockito.when(service.saveOrUpdate(Mockito.any(Book.class))).thenReturn(theBook);

		mvc.perform(put("/api/v1/books").contentType(MediaType.APPLICATION_JSON).content(toJson(theBook)))
				.andExpect(status().isCreated());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#removeBook(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveBook() throws Exception {
		Book theBook = new Book();
		theBook.setId(1L);
		theBook.setName("Test");

		Mockito.doNothing().when(service).remove(1L);

		mvc.perform(delete("/api/v1/books/" + theBook.getId().toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.BookController#testRemoveBookWhenBookDoesNotExist(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveBookWhenBookDoesNotExist() throws Exception {
		Mockito.doThrow(new BookNotFoundException("Book Not Found, Invalid Book ID")).when(service).remove(11L);
		mvc.perform(delete("/api/v1/books/" + 11).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	private static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
