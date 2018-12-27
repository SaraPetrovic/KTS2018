package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.research.ws.wadl.Application;

import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;
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
	
	@PutMapping("/activate/{id}")
	public ResponseEntity<Void> activateTicket(@PathVariable Long id){
		Ticket ticket = ticketService.findById(id);
		ticketService.activateTicket(ticket);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@Consumes("application/json")
	public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket){
		
		if(ticket.getTransportType() == null || ticket.getTicketTemporal() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
		if(ticket instanceof LineTicket) {
			if(((LineTicket) ticket).getLine() == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				Line line = lineService.findById(((LineTicket) ticket).getLine().getId());
				((LineTicket) ticket).setLine(line);
			}
		}
		if(ticket instanceof ZoneTicket) {
			if(((ZoneTicket) ticket).getZone() == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				Zone zone = zoneService.findById(((ZoneTicket) ticket).getZone().getId());
				((ZoneTicket) ticket).setZone(zone);
			}
		}
		Ticket rez = ticketService.addTicket(ticket);
		if(rez == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(rez, HttpStatus.OK);
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
