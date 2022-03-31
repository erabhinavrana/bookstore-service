/**
 * 
 */
package com.smartdubai.bookstore.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.smartdubai.bookstore.entity.Book;

import lombok.Builder;
import lombok.Data;

/**
 * @author abhinav
 *
 */
@Data
@Builder
public class CheckoutRequest {
	@NotEmpty(message = "Books are not added in the cart!")
	private List<Book> books;
	private String promoCode;
}
