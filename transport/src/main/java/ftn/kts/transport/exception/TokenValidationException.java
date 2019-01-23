package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class TokenValidationException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public TokenValidationException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public TokenValidationException(String message) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
