
/**
 * 
 */
package com.kxtx.boot.exception;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
