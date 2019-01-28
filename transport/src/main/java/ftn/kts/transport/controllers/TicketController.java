package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.MyTicketDTO;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.services.TicketService;
import ftn.kts.transport.services.UserService;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private DTOConverter dtoConverter;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PutMapping(path = "/rest/ticket/activate/{id}")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<MyTicketDTO> activateTicket(@PathVariable Long id){
		Ticket ticket = ticketService.findById(id);
		Ticket rez = ticketService.activateTicket(ticket);
		return new ResponseEntity<MyTicketDTO>(new MyTicketDTO(rez), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(path = "/rest/ticket/buy")
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseEntity<Ticket> buyTicket(@RequestBody TicketDTO ticketDTO, @RequestHeader("Authorization") String token){
		Ticket ticket = dtoConverter.convertDTOtoTicket(ticketDTO);
		Ticket ret = ticketService.buyTicket(ticket, token);
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/ticket/types")
	public ResponseEntity<List<String>> getAllTicketTypes(){
		List<String> types = new ArrayList<String>();
		for(TicketTypeTemporal type : TicketTypeTemporal.values()) {
			types.add(type.toString());
		}
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
	
	

    @GetMapping(path = "/check/{id}")
    @Produces("application/json")
    @CrossOrigin( origins = "http://localhost:4200")
	@PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<Ticket> checkTicket(@PathVariable String id){

	    try{
            Ticket ret = this.ticketService.findById(ticketService.decodeId(id));

            return ResponseEntity.ok(ret);
        }catch(Exception e){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
	}

    @GetMapping(path = "rest/ticket/me")
    @Produces("application/json")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @CrossOrigin( origins = "http://localhost:4200")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
	public ResponseEntity<List<MyTicketDTO>> getMyTickets(@RequestHeader("Authorization") String token){

	    try {

            List<MyTicketDTO> ret = new ArrayList<>();
            User user = this.userService.getUser(token);

            List<Ticket> tickets = this.ticketService.getTickets(user);
            System.out.println(tickets.size());
            for (Ticket t : tickets) {

            	String qrcode = ticketService.generateQrCode(t.getId());
                ret.add(new MyTicketDTO(t, qrcode));


            }

            return ResponseEntity.ok(ret);
        } catch(Exception e){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
	}
    


}
