package ftn.kts.transport.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.exception.ZoneNotFoundException;

@ControllerAdvice
public class ExceptionController {

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
