package ftn.kts.transport.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.services.RouteScheduleService;

@RestController
public class RouteScheduleController {
	
	@Autowired
	private RouteScheduleService rsService;
	
	@Autowired
	private DTOConverter dtoConverter;

	@GetMapping(path = "/schedule/line/{id}")
	@Produces("application/json")
	public ResponseEntity<List<RouteSchedule>> getSchedulesForLine(@PathVariable("id") long id) {
		List<RouteSchedule> schedules = rsService.getScheduleByLine(id);
		return new ResponseEntity<List<RouteSchedule>>(schedules, HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/rest/schedule/line/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RouteSchedule> addSchedule(@PathVariable("id") long id, @RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = rsService.addSchedule(schedule, id);
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.CREATED);
	}
	
	// OVO SE NE KORISTI - NEMA UPDATE SCHEDULE-a.....................................................
	@PutMapping(path = "/rest/line/{lineId}/schedule/{scheduleId}/update")
	@Consumes("application/json")
	@Produces("application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RouteSchedule> updateSchedule(@PathVariable("lineId") long lineId, 
														@PathVariable("scheduleId") long scheduleId,
														@RequestBody RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = dtoConverter.convertDTOtoRouteSchedule(scheduleDTO);
		RouteSchedule ret = rsService.updateSchedule(schedule, lineId, scheduleId);
		
		return new ResponseEntity<RouteSchedule>(ret, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/rest/schedule/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> deleteSchedule(@PathVariable("id") long scheduleId) {
		
		boolean ret = rsService.deleteSchedule(scheduleId);
		return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
	}

}
