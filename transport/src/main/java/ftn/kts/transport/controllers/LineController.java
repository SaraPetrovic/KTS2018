package ftn.kts.transport.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


	@PostMapping(value = "/line")
	@Consumes("application/json")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Line> addLine(@RequestBody LineDTO lineDTO) {
		System.out.println("usao sam ovde");
		Line ret = null;
		Line l = dtoConverter.convertDTOtoLine(lineDTO);

		ret = lineService.addLine(l);

		ret = lineService.addStationsToLine(ret.getId(), lineDTO);
		
		return new ResponseEntity<Line>(ret, HttpStatus.CREATED);
	}

	@PostMapping(value = "/line/{id}/update")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<Line> updateLine(@RequestBody LineDTO updatedLine, @PathVariable("id") long id) {
		Line ret = lineService.updateLine(updatedLine, id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}

	@DeleteMapping(value = "/line/{id}/delete")
	@Produces("application/json")
	public ResponseEntity<Line> deleteLine(@PathVariable("id") long id) {
		Line ret = lineService.deleteLine(id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}


	@PostMapping(value = "/line/{id}/updateStations")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<Line> updateStations(@PathVariable("id") long id, @RequestBody LineDTO newStations) {
		Line ret = lineService.updateLineStations(id, newStations);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	

	@GetMapping(value = "/line/{id}/schedules")
	@Produces("application/json")
	public ResponseEntity<List<RouteSchedule>> getSchedulesForLine(@PathVariable("id") long id) {
		List<RouteSchedule> schedules = lineService.getScheduleByLine(id);
		return new ResponseEntity<List<RouteSchedule>>(schedules, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/line/{id}/addSchedule")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<RouteSchedule> addSchedule(@PathVariable("id") long id, @RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = lineService.addSchedule(schedule, id);
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/line/{lineId}/schedule/{scheduleId}/update")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<RouteSchedule> updateSchedule(@PathVariable("lineId") long lineId, 
														@PathVariable("scheduleId") long scheduleId,
														@RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = lineService.updateSchedule(schedule, lineId, scheduleId);
		
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/line/{lineId}/schedule/{scheduleId}/delete")
	public ResponseEntity<Boolean> deleteSchedule(@PathVariable("scheduleId") long scheduleId) {
		
		boolean ret = lineService.deleteSchedule(scheduleId);
		return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
	}
	
}
