package ftn.kts.transport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.services.LineService;

@RestController
public class LineController {

	@Autowired
	private LineService lineService;
	@Autowired
	private DTOConverter dtoConverter;

	
	
	@GetMapping(value = "/line")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Line>> getLInes(){
		return ResponseEntity.status(HttpStatus.OK).body(this.lineService.getAllLines());
	}


	@RequestMapping(
			value = "/line",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Line> addLine(@RequestBody LineDTO lineDTO) {
		System.out.println("usao sam ovde");
		Line ret = null;
		Line l = dtoConverter.convertDTOtoLine(lineDTO);

		ret = lineService.addLine(l);

		ret = lineService.addStationsToLine(ret.getId(), lineDTO);
		
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
			value = "/line/{id}/delete",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Line> deleteLine(@PathVariable("id") long id) {
		Line ret = lineService.deleteLine(id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/line/{id}/updateStations",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Line> updateStations(@PathVariable("id") long id, @RequestBody LineDTO newStations) {
		Line ret = lineService.updateLineStations(id, newStations);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/line/{id}/schedules",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<RouteSchedule>> getSchedulesForLine(@PathVariable("id") long id) {
		List<RouteSchedule> schedules = lineService.getScheduleByLine(id);
		return new ResponseEntity<List<RouteSchedule>>(schedules, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/line/{id}/addSchedule",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<RouteSchedule> addSchedule(@PathVariable("id") long id, @RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = lineService.addSchedule(schedule, id);
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/line/{lineId}/schedule/{scheduleId}/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<RouteSchedule> updateSchedule(@PathVariable("lineId") long lineId, 
														@PathVariable("scheduleId") long scheduleId,
														@RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = lineService.updateSchedule(schedule, lineId, scheduleId);
		
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/line/{lineId}/schedule/{scheduleId}/delete",
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Boolean> deleteSchedule(@PathVariable("scheduleId") long scheduleId) {
		
		boolean ret = lineService.deleteSchedule(scheduleId);
		return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
	}
	
}
