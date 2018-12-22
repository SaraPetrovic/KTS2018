package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class DAOException extends RuntimeException {
	
	private HttpStatus httpStatus;

	
	public DAOException(String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}
	
	public DAOException(String errorMessage) {
		super(errorMessage);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
