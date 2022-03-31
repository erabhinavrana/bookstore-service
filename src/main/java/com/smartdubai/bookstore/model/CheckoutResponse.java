/**
 * 
 */
package com.smartdubai.bookstore.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author abhinav
 *
 */
@Data
@Builder
public class CheckoutResponse {
	private Double totalAmount;
}
