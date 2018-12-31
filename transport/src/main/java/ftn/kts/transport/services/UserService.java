package ftn.kts.transport.services;

import java.util.Set;

import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;

public interface UserService  {

    void addUser(String username, String password, String first_name, String last_name);
    User login(String usename, String password);
	User save(User user);
	User findById(Long id);
	Set<Ticket> getTickets(Long id);
}
