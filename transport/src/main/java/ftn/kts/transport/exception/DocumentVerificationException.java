package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class DocumentVerificationException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public DocumentVerificationException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public DocumentVerificationException(String message) {
		super(message);
		this.httpStatus = HttpStatus.CONFLICT;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
