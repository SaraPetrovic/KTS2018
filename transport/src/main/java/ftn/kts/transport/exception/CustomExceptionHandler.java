package ftn.kts.transport.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(DAOException.class)
	public ResponseEntity<ExceptionResponse> handleDAOException(DAOException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(InvalidInputDataException.class)
	public ResponseEntity<ExceptionResponse> handleInputDataException(InvalidInputDataException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
}
