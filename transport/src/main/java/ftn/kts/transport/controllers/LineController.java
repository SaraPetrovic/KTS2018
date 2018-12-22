package ftn.kts.transport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.services.LineService;

@RestController
public class LineController {

	@Autowired
	private LineService lineService;
	
	@RequestMapping(
			value = "/line",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Line> addLine(@RequestBody LineDTO newLine) {
		
		Line ret = null;

		ret = lineService.addLine(newLine);

		ret = lineService.addStationsToLine(ret.getId(), newLine);
		
		return new ResponseEntity<Line>(ret, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/line/{id}/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Line> updateLine(@RequestBody LineDTO updatedLine, @PathVariable("id") long id) {
		Line ret = lineService.updateLine(updatedLine, id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/line/delete",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Line> deleteLine(@RequestBody LineDTO toDeleteLine) {
		Line ret = lineService.deleteLine(toDeleteLine);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	
}
