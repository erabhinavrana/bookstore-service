/**
 * 
 */
package com.smartdubai.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartdubai.bookstore.entity.Book;

/**
 * @author abhinav
 *
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
