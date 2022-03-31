/**
 * 
 */
package com.smartdubai.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.exception.BookNotFoundException;
import com.smartdubai.bookstore.repo.BookRepository;
import com.smartdubai.bookstore.service.BookService;

/**
 * @author abhinav
 *
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> fetchAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book fetchBookById(Long id) {
		Optional<Book> findById = bookRepository.findById(id);
		return findById.orElseThrow(() -> new BookNotFoundException("Book Not Found, Invalid Book ID"));
	}

	@Override
	public Book saveOrUpdate(Book book) {
		if (book.getId() == null) {
			String ISBN = UUID.randomUUID().toString();
			book.setISBN(ISBN);
		}
		Book savedBook = bookRepository.save(book);
		return savedBook;
	}

	@Override
	public void remove(Long id) {
		Optional<Book> findById = bookRepository.findById(id);
		if (findById.isPresent()) {
			bookRepository.deleteById(id);
		} else {
			throw new BookNotFoundException("Book Not Found, Invalid Book ID");
		}
	}

}
