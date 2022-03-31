/**
 * 
 */
package com.smartdubai.bookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.exception.CheckoutException;
import com.smartdubai.bookstore.feign.proxy.PromoProxy;
import com.smartdubai.bookstore.model.CheckoutRequest;
import com.smartdubai.bookstore.model.CheckoutResponse;
import com.smartdubai.bookstore.model.Promo;

/**
 * @author abhinav
 *
 */
@ExtendWith(SpringExtension.class)
class CheckoutServiceImplTest {

	@Mock
	private PromoProxy promoProxy;

	@InjectMocks
	private CheckoutServiceImpl checkoutServiceImpl;

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.CheckoutServiceImpl#checkout(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 */
	@Test
	void testCheckout() {
		Book theBook = new Book();
		theBook.setId(1l);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(10d);
		CheckoutRequest request = CheckoutRequest.builder().books(Arrays.asList(theBook)).promoCode("TESTPROMO")
				.build();

		Promo thePromo = Promo.builder().promoType("Test_Type").promoValue(10f).maxDiscount(10d).build();

		when(promoProxy.getPromoDetails(Mockito.anyString())).thenReturn(thePromo);
		CheckoutResponse response = checkoutServiceImpl.checkout(request);
		assertNotNull(response);
		assertEquals(9, response.getTotalAmount());
		verify(promoProxy).getPromoDetails(Mockito.anyString());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.CheckoutServiceImpl#testCheckout_WithoutPromoCode(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 */
	@Test
	void testCheckout_WithoutPromoCode() {
		Book theBook = new Book();
		theBook.setId(1l);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(10d);
		CheckoutRequest request = CheckoutRequest.builder().books(Arrays.asList(theBook)).build();
		CheckoutResponse response = checkoutServiceImpl.checkout(request);
		assertNotNull(response);
		assertEquals(10, response.getTotalAmount());
		verify(promoProxy, times(0)).getPromoDetails(Mockito.anyString());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.CheckoutServiceImpl#testCheckout_WithoutPromoCode(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 */
	@Test
	void testCheckout_WithMaxDiscount() {
		Book theBook = new Book();
		theBook.setId(1l);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(20d);
		CheckoutRequest request = CheckoutRequest.builder().books(Arrays.asList(theBook)).promoCode("TESTPROMO")
				.build();

		Promo thePromo = Promo.builder().promoType("Test_Type").promoValue(10f).maxDiscount(1d).build();

		when(promoProxy.getPromoDetails(Mockito.anyString())).thenReturn(thePromo);
		CheckoutResponse response = checkoutServiceImpl.checkout(request);
		assertNotNull(response);
		assertEquals(19, response.getTotalAmount());
		verify(promoProxy, times(1)).getPromoDetails(Mockito.anyString());
	}

	/**
	 * Test method for
	 * {@link com.smartdubai.bookstore.service.impl.CheckoutServiceImpl#testCheckout_WithoutPromoCode(com.smartdubai.bookstore.model.CheckoutRequest)}.
	 */
	@Test
	void testCheckout_WithInvalidPromoCode() {
		Book theBook = new Book();
		theBook.setId(1l);
		theBook.setName("Test");
		theBook.setAuthor("Test Author");
		theBook.setDescription("Test description");
		theBook.setType("Test_Type");
		theBook.setPrice(20d);
		CheckoutRequest request = CheckoutRequest.builder().books(Arrays.asList(theBook)).promoCode("TESTPROMO")
				.build();

		when(promoProxy.getPromoDetails(Mockito.anyString())).thenThrow(new RuntimeException("Invalid PromoCode!"));
		assertThrows(CheckoutException.class, () -> {
			checkoutServiceImpl.checkout(request);
		});
		verify(promoProxy, times(1)).getPromoDetails(Mockito.anyString());
	}

}
