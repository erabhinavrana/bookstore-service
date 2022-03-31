/**
 * 
 */
package com.smartdubai.bookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartdubai.bookstore.model.CheckoutRequest;
import com.smartdubai.bookstore.model.CheckoutResponse;
import com.smartdubai.bookstore.service.CheckoutService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abhinav
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("/checkout")
	public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
		log.info("Inside checkout method");
		return checkoutService.checkout(request);
	}
}
