package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.services.LineService;
import ftn.kts.transport.services.TicketService;
import ftn.kts.transport.services.ZoneService;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private LineService lineService;
	@Autowired
	private DTOConverter dtoConverter;
	
	@PutMapping("/activate/{id}")
	public ResponseEntity<Void> activateTicket(@PathVariable Long id){
		Ticket ticket = ticketService.findById(id);
		ticketService.activateTicket(ticket);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/buyTicket",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Ticket> buyTicket(@RequestBody TicketDTO ticketDTO){
		Ticket ticket = dtoConverter.convertDTOtoTicket(ticketDTO);
		Ticket ret = ticketService.buyTicket(ticket);
		return null;
	}
	
	@GetMapping("/types")
	public ResponseEntity<List<String>> getAllTicketTypes(){
		List<String> types = new ArrayList<String>();
		for(TicketTypeTemporal type : TicketTypeTemporal.values()) {
			types.add(type.toString());
		}
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
	
	
	
}
