package ftn.kts.transport.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private HttpServletRequest request;
	//@Autowired
	//private PriceListService priceListService;
	@Autowired
	private UserService userService;
	
	@Override
	public Ticket addTicket(Ticket ticket) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("user");

		ticket.setUser(sessionUser);
		ticket.setActive(false);
		
		double price = 0;// = priceListService.getPriceForTicket();
		//ticket.setPrice(price);
		
		if(sessionUser.getMoneyBalance() < price)
			return null;
		
		sessionUser.setMoneyBalance(sessionUser.getMoneyBalance() - price);
		sessionUser.getTickets().add(ticket);
		
		userService.save(sessionUser);
		ticketRepository.save(ticket);
		
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
