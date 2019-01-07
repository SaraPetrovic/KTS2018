package ftn.kts.transport.services;

import org.springframework.stereotype.Service;

import ftn.kts.transport.model.Ticket;

@Service
public interface TicketService {
	Ticket findById(Long id);
	void activateTicket(Ticket ticket);
	Ticket buyTicket(Ticket ticket);
}
