/**
 * 
 */
package com.smartdubai.bookstore.exception;

/**
 * @author abhinav
 *
 */
public class CheckoutException extends RuntimeException {

	private static final long serialVersionUID = -9216090443503766422L;

	/**
	 * 
	 */
	public CheckoutException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public CheckoutException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public CheckoutException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CheckoutException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public CheckoutException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
