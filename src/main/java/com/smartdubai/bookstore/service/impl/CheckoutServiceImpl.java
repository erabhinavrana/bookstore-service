/**
 * 
 */
package com.smartdubai.bookstore.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartdubai.bookstore.entity.Book;
import com.smartdubai.bookstore.exception.CheckoutException;
import com.smartdubai.bookstore.feign.proxy.PromoProxy;
import com.smartdubai.bookstore.model.CheckoutRequest;
import com.smartdubai.bookstore.model.CheckoutResponse;
import com.smartdubai.bookstore.model.Promo;
import com.smartdubai.bookstore.service.CheckoutService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abhinav
 *
 */
@Slf4j
@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	private PromoProxy promoProxy;

	/**
	 * @param checkoutRequest
	 * @return
	 */
	@Override
	public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
		log.info("Inside checkout method: {}", checkoutRequest);
		Map<String, Double> pricePerBookType = checkoutRequest.getBooks().stream()
				.collect(Collectors.groupingBy(Book::getType, Collectors.summingDouble(Book::getPrice)));

		validatePromoCode(checkoutRequest, pricePerBookType);

		Double totalAmount = pricePerBookType.values().stream().mapToDouble(d -> d).sum();

		return CheckoutResponse.builder().totalAmount(totalAmount).build();
	}

	/**
	 * @param checkoutRequest
	 * @param pricePerBookType
	 */
	private void validatePromoCode(CheckoutRequest checkoutRequest, Map<String, Double> pricePerBookType) {
		log.info("Inside validatePromoCode method");
		if (checkoutRequest.getPromoCode() != null) {
			try {
				Promo thePromo = promoProxy.getPromoDetails(checkoutRequest.getPromoCode());
				Float promoValue = thePromo.getPromoValue();
				Double originalAmount = pricePerBookType.get(thePromo.getPromoType());
				Double discountedAmount = calculateDiscount(promoValue, thePromo.getMaxDiscount(), originalAmount);
				log.info("Discounted Amount: {}", discountedAmount);
				pricePerBookType.replace(thePromo.getPromoType(), discountedAmount);

			} catch (Exception ex) {
				log.info("Inside validatePromoCode method !!!!!!!!!! {}", ex.getMessage());
				throw new CheckoutException(ex);
			}
		}
	}

	/**
	 * @param promoValue
	 * @param maxDiscount
	 * @param originalAmount
	 * @return
	 */
	private Double calculateDiscount(Float promoValue, Double maxDiscount, Double originalAmount) {
		log.info("Inside calculateDiscount method");
		double discount = (originalAmount * promoValue / 100);

		if (discount > maxDiscount) {
			discount = maxDiscount;
		}

		return (originalAmount - discount);
	}
}
