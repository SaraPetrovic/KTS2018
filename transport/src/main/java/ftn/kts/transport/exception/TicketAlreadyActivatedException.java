package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class TicketAlreadyActivatedException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public TicketAlreadyActivatedException(String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}
	
	public TicketAlreadyActivatedException(String errorMessage) {
		super(errorMessage);
		this.httpStatus = HttpStatus.FORBIDDEN;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}
