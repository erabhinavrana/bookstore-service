/**
 * 
 */
package com.smartdubai.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author abhinav
 *
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
	private int status;
	private String message;
	private long timestamp;
}
