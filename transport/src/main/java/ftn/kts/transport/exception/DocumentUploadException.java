package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;

public class DocumentUploadException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public DocumentUploadException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public DocumentUploadException(String message) {
		super(message);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
