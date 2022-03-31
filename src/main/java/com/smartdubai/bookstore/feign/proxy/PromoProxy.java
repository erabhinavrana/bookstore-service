/**
 * 
 */
package com.smartdubai.bookstore.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartdubai.bookstore.model.Promo;

/**
 * @author abhinav
 *
 */
@FeignClient(name = "promo-service")
public interface PromoProxy {

	@GetMapping("/api/v1/promos/{code}")
	public Promo getPromoDetails(@PathVariable String code);

}
