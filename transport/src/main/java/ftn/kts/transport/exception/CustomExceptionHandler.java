package ftn.kts.transport.exception;

import org.springframework.http.HttpStatus;
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
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthorizationException(AuthorizationException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(DocumentUploadException.class)
	public ResponseEntity<ExceptionResponse> handleDocumentUploadException(DocumentUploadException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}

	
	@ExceptionHandler(TicketAlreadyActivatedException.class)
	public ResponseEntity<ExceptionResponse> handleTicketAlreadyActivated(TicketAlreadyActivatedException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	
	@ExceptionHandler(DocumentVerificationException.class)
	public ResponseEntity<ExceptionResponse> handleDocumentAlreadyVerified(DocumentVerificationException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(ZoneNotFoundException.class)
	public ResponseEntity<Error> zoneNotFound(ZoneNotFoundException e){
		
		Long zoneId = e.getZoneId();
		Error error = new Error("Zone [" + zoneId + "] not found.");
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(StationNotFoundException.class)
	public ResponseEntity<Error> stationNotFound(StationNotFoundException e){
		
		Long stationId = e.getStationId();
		Error error = new Error("Station [" + stationId + "] not found.");
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}

	
}
