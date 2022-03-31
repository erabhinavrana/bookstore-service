/**
 * 
 */
package com.smartdubai.bookstore.service;

import java.util.List;

import com.smartdubai.bookstore.entity.Book;

/**
 * @author abhinav
 *
 */
public interface BookService {

	List<Book> fetchAllBooks();

	Book fetchBookById(Long id);

	Book saveOrUpdate(Book book);

	void remove(Long id);
}
