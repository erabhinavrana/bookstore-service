/**
 * 
 */
package com.smartdubai.bookstore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import com.smartdubai.bookstore.exception.CheckoutException;
import com.smartdubai.bookstore.model.CheckoutRequest;
import com.smartdubai.bookstore.model.CheckoutResponse;
import com.smartdubai.bookstore.service.CheckoutService;

/**
 * @author abhinav
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CheckoutService service;

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.CheckoutController#checkout(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	@Test
	void testCheckout() throws IOException, Exception {

		Book theBook = new Book();
		theBook.setName("Test");
		List<Book> allBooks = Arrays.asList(theBook);

		when(service.checkout(Mockito.any(CheckoutRequest.class)))
				.thenReturn(CheckoutResponse.builder().totalAmount(10d).build());

		mvc.perform(post("/api/v1/checkout").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(CheckoutRequest.builder().books(allBooks).promoCode("TEST-TYPE").build())))
				.andExpect(status().isOk()).andExpect(jsonPath("totalAmount", is(10d)));

	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.controller.CheckoutController#checkout(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	@Test
	void testCheckout_WithBadRequest() throws IOException, Exception {

		Book theBook = new Book();
		theBook.setName("Test");
		List<Book> allBooks = Arrays.asList(theBook);

		Mockito.doThrow(new CheckoutException("Invalid Promo Code")).when(service)
				.checkout(Mockito.any(CheckoutRequest.class));

		mvc.perform(post("/api/v1/checkout").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(CheckoutRequest.builder().books(allBooks).promoCode("TEST-TYPE").build())))
				.andExpect(status().isBadRequest());
	}

	private static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
