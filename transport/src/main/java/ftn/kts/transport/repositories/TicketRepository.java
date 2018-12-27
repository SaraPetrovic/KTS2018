package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Ticket;

@Component
public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
