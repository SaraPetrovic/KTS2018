package ftn.kts.transport.services;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.TicketRepository;
import ftn.kts.transport.security.JwtValidator;


@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private PriceListService priceListService;
	@Autowired
	private JwtValidator jwtValidator;
	@Autowired
	private UserService userService;
	
	@Override
	public Ticket buyTicket(Ticket ticket, String token) {

		User logged = getUser(token);
		
		// mesecne i godisnje karte mogu kupiti samo Useri kojima je approved verification document!
		if (ticket.getTicketTemporal().ordinal() != 0) {
			if (logged.getDocumentVerified().ordinal() == 0) {
				throw new InvalidInputDataException("User's personal document is not uploaded! Only"
						+ " One-hour ticket can be purchased if User hasn't uploaded personal document!", 
						HttpStatus.FORBIDDEN);
			} else if (logged.getDocumentVerified().ordinal() == 1) {
				throw new InvalidInputDataException("User's personal document has not been verified yet! Only"
						+ " One-hour ticket can be purchased if personal document is not verified!", 
						HttpStatus.FORBIDDEN);
			} else if (logged.getDocumentVerified().ordinal() == 2) {
				throw new InvalidInputDataException("User's personal document has been rejected! Try"
						+ " uploading document again! One-hour tickets can be purchased without personal document", 
						HttpStatus.FORBIDDEN);
			} 
		}
		
		
		ticket.setUser(logged);
		
		double calculatedPrice = priceListService.calculateTicketPrice(ticket);
		ticket.setPrice(calculatedPrice);
		
		return ticketRepository.save(ticket);
	
	}
	
	@Override
	public Ticket activateTicket(Ticket ticket) {
		ticket.setActive(true);
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date currentDate = new Date();
		Date endDate = Date.from(currentDate.toInstant().plus(Duration.ofHours(1)));
		System.out.println("Current date " + sdf.format(currentDate) + " + 1 hour : " + sdf.format(endDate));
		ticket.setStartTime(currentDate);
		ticket.setEndTime(endDate);
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket findById(Long id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		return ticket.orElseThrow(() -> new DAOException("Ticket [id=" + id + "] not found!", HttpStatus.NOT_FOUND));
	}

	@Override
	public User getUser(String token) {
		User credentials = jwtValidator.validate(token.substring(7));
		User ret = userService.findByUsername(credentials.getUsername());
		return ret;
	}


	@Override
    public List<Ticket> getTickets(User user){
	    return this.ticketRepository.findByUser(user);
	}
}
