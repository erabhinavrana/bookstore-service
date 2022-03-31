/**
 * 
 */
package com.smartdubai.bookstore.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.exception.BookNotFoundException;
import com.smartdubai.bookstore.repo.BookRepository;

/**
 * @author abhinav
 *
 */
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookServiceImpl serviceImpl;

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#fetchAllBooks()}.
	 */
	@Test
	void testFetchAllBooks() {
		Book book = new Book();
		book.setName("Test");
		when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
		List<Book> fetchAllBooks = serviceImpl.fetchAllBooks();
		assertNotNull(fetchAllBooks);
		assertEquals(1, fetchAllBooks.size());
		verify(bookRepository).findAll();
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#fetchBookById(java.lang.Long)}.
	 */
	@Test
	void testFetchBookById() {
		Book book = new Book();
		book.setId(1l);
		book.setName("Test");
		when(bookRepository.findById(Mockito.any())).thenReturn(Optional.of(book));
		Book theBook = serviceImpl.fetchBookById(1l);
		assertNotNull(theBook);
		assertThat(book.getName()).isSameAs(theBook.getName());
		verify(bookRepository).findById(1l);
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#fetchBookById(java.lang.Long)}.
	 */
	@Test
	void testFetchBookById_WithInvalidBookId() {
		when(bookRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, () -> {
			serviceImpl.fetchBookById(1l);
		});
		verify(bookRepository).findById(Mockito.any());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#testSave(com.smartdubai.bookstore.entity.Book)}.
	 */
	@Test
	void testSave() {
		Book book = new Book();
		book.setName("Test");
		when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
		Book theBook = serviceImpl.saveOrUpdate(book);
		assertNotNull(theBook);
		assertThat(book.getName()).isSameAs(theBook.getName());
		verify(bookRepository).save(book);
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#testUpdate(com.smartdubai.bookstore.entity.Book)}.
	 */
	@Test
	void testUpdate() {
		Book book = new Book();
		book.setId(1l);
		book.setName("Test");
		when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
		Book theBook = serviceImpl.saveOrUpdate(book);
		assertNotNull(theBook);
		assertThat(book.getName()).isSameAs(theBook.getName());
		verify(bookRepository).save(Mockito.any(Book.class));
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#remove(java.lang.Long)}.
	 */
	@Test
	void testRemove() {
		Book book = new Book();
		book.setId(1l);
		book.setName("Test");
		when(bookRepository.findById(Mockito.any())).thenReturn(Optional.of(book));
		doNothing().when(bookRepository).deleteById(1l);
		serviceImpl.remove(1l);
		verify(bookRepository).deleteById(1l);
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.BookServiceImpl#testRemove_WithInvalidBookId(java.lang.Long)}.
	 */
	@Test
	void testRemove_WithInvalidBookId() {
		when(bookRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		doNothing().when(bookRepository).deleteById(1l);
		assertThrows(BookNotFoundException.class, () -> {
			serviceImpl.remove(1l);
		});
		verify(bookRepository, times(0)).deleteById(1l);
	}

}
