/**
 * 
 */
package com.smartdubai.bookstore.service;

import com.smartdubai.bookstore.model.CheckoutRequest;
import com.smartdubai.bookstore.model.CheckoutResponse;

/**
 * @author abhinav
 *
 */
public interface CheckoutService {
	CheckoutResponse checkout(CheckoutRequest checkoutRequest);
}
