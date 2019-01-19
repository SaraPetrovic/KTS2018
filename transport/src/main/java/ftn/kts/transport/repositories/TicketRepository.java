package ftn.kts.transport.repositories;

import ftn.kts.transport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Ticket;

import java.util.List;

@Component
public interface TicketRepository extends JpaRepository<Ticket, Long>{


    List<Ticket> findByUser(User user);
}
