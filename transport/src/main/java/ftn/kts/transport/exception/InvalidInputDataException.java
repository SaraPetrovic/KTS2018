package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputDataException extends RuntimeException {

	private HttpStatus httpStatus;

	public InvalidInputDataException(String errorMessage) {
		super(errorMessage);
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public InvalidInputDataException(String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
