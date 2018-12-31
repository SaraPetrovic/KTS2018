package ftn.kts.transport.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.repositories.TicketRepository;
import ftn.kts.transport.services.TicketService;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TicketServiceTest {
	
	@Autowired
	private TicketService ticketService;
	
	@MockBean 
	private TicketRepository ticketRepository;
	
	@MockBean
	private UserService userService;
	
	

}
