package ftn.kts.transport.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.BaseEncoding;
import ftn.kts.transport.model.User;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.services.TicketService;

@RestController
@RequestMapping(value = "rest/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private DTOConverter dtoConverter;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/activate/{id}")
	public ResponseEntity<Ticket> activateTicket(@PathVariable Long id){
		Ticket ticket = ticketService.findById(id);
		ticketService.activateTicket(ticket);
		return new ResponseEntity<Ticket>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@RequestMapping(
			value = "/buyTicket",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Ticket> buyTicket(@RequestBody TicketDTO ticketDTO, @RequestHeader("Authorization") String token){
		Ticket ticket = dtoConverter.convertDTOtoTicket(ticketDTO);
		Ticket ret = ticketService.buyTicket(ticket, token);
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
	
	@GetMapping("/types")
	public ResponseEntity<List<String>> getAllTicketTypes(){
		List<String> types = new ArrayList<String>();
		for(TicketTypeTemporal type : TicketTypeTemporal.values()) {
			types.add(type.toString());
		}
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
	
	
    @GetMapping(
            value = "/check/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin( origins = "http://localhost:4200")
	@PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<Ticket> checkTicket(@PathVariable String id){

	    try{
            Ticket ret = this.ticketService.findById(decodeId(id));

            return ResponseEntity.ok(ret);
        }catch(Exception e){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
	}

	@GetMapping(
			value = "/me",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<List<TicketDTO>> getMyTickets(@RequestHeader("Authorization") final String token){

	    try {
            List<TicketDTO> ret = new ArrayList<>();
            User user = this.ticketService.getUser(token);

            List<Ticket> tickets = this.ticketService.getTickets(user);

            for (Ticket t : tickets) {
            	System.out.println(generateQrCode(t.getId()));
                ret.add(new TicketDTO(t, generateQrCode(t.getId()).getPath()));
            }

            return ResponseEntity.ok(ret);
        } catch(Exception e){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
	}

    private File generateQrCode(Long id) {

        String encodedID = BaseEncoding.base64()
                .encode(("TicketID=" + id.toString()).getBytes());

        File qrCode = QRCode.from(encodedID).to(ImageType.JPG).withSize(250, 250).file();

        return qrCode;
    }

    private Long decodeId(String encodedID){

	    byte[] decodedID = BaseEncoding.base64()
                .decode(encodedID);

	    String stringID = new String(decodedID);

	    return Long.parseLong(stringID.substring(9));
    }

}
