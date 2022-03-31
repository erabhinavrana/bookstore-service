/**
 * 
 */
package com.smartdubai.bookstore.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author abhinav
 *
 */
@Data
@Builder
public class Promo {
	private Long id;
	private String promoCode;
	private String promoType;
	private Float promoValue;
	private Double maxDiscount;
	private Date expiry;
}
