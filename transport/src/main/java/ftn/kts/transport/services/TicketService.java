package ftn.kts.transport.services;

import org.springframework.stereotype.Service;

import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;

@Service
public interface TicketService {
	Ticket findById(Long id);
	void activateTicket(Ticket ticket);
	Ticket buyTicket(Ticket ticket, String token);
	User getUser(String token);
}
