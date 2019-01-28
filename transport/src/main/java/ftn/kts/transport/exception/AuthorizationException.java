package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public AuthorizationException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}
	
	public AuthorizationException(String message) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
