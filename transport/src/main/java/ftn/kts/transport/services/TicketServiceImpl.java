package ftn.kts.transport.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;
import ftn.kts.transport.repositories.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private LineService lineService;
	@Autowired
	private UserService userService;
	@Autowired
	private PriceListService priceListService;
	
	@Override
	public Ticket buyTicket(Ticket ticket) {

		if (ticket instanceof LineTicket) {
			Line l = lineService.findById(((LineTicket) ticket).getLine().getId());
			((LineTicket) ticket).setLine(l);
		} else if (ticket instanceof ZoneTicket) {
			Zone z = zoneService.findById(((ZoneTicket) ticket).getZone().getId());
			((ZoneTicket) ticket).setZone(z);
		}
		
		double calculatedPrice = priceListService.calculateTicketPrice(ticket);
		ticket.setPrice(calculatedPrice);
		
		// provera za usera da li ima dovoljno sredstava na racunu
		// skidanje sa racuna
		// i kacenje usera
		
		
		return ticket;
	
	}
	
	@Override
	public void activateTicket(Ticket ticket) {
		ticket.setActive(true);
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date currentDate = new Date();
		Date endDate = Date.from(currentDate.toInstant().plus(Duration.ofHours(1)));
		System.out.println("Current date " + sdf.format(currentDate) + " + 1 hour : " + sdf.format(endDate));
		ticket.setStartTime(currentDate);
		ticket.setEndTime(endDate);
		ticketRepository.save(ticket);
	}

	@Override
	public Ticket findById(Long id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		return ticket.orElseThrow(() -> new DAOException("Ticket[id=" + id + "] not found!", HttpStatus.NOT_FOUND));
	}
	
}
