/**
 * 
 */
package com.smartdubai.bookstore.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.service.BookService;

/**
 * @author abhinav
 *
 */
@RestController
@RequestMapping("/api/v1")
public class BookController {

	@Autowired
	private BookService bookService;

	/**
	 * @return
	 */
	@GetMapping("/books")
	List<Book> listAllBooks() {
		List<Book> theBooksList = bookService.fetchAllBooks();
		return theBooksList;
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/books/{id}")
	EntityModel<Book> listBookById(@PathVariable Long id) {
		Book theBook = bookService.fetchBookById(id);
		return EntityModel.of(theBook).add(linkTo(methodOn(this.getClass()).listAllBooks()).withRel("all-books"));
	}

	/**
	 * @param book
	 * @return
	 */
	@PostMapping("/books")
	ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
		book.setId(null);
		Book theBook = bookService.saveOrUpdate(book);
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(theBook.getId()).toUri())
				.build();
	}

	/**
	 * @param book
	 * @return
	 */
	@PutMapping("/books")
	ResponseEntity<?> updateBook(@Valid @RequestBody Book book) {
		Book theBook = bookService.saveOrUpdate(book);
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(theBook.getId()).toUri())
				.build();
	}

	/**
	 * @param id
	 */
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/books/{id}")
	void removeBook(@PathVariable Long id) {
		bookService.remove(id);
	}

}
