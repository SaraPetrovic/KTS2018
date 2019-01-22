package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.enums.DocumentVerification;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.Role;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;
import ftn.kts.transport.repositories.TicketRepository;
import ftn.kts.transport.services.JwtGeneratorService;
import ftn.kts.transport.services.PriceListService;
import ftn.kts.transport.services.TicketService;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TicketServiceUnitTest {
	
	@Autowired
	private TicketService ticketService;
	@MockBean 
	private TicketRepository ticketRepositoryMocked;
	@MockBean
	private UserService userServiceMocked;
	@MockBean
	private PriceListService priceListServiceMocked;
	@MockBean
	private JwtGeneratorService jwtServiceMocked;
	
	
	private User user = new User();
	private final String TOKEN = "token XYZ";
	private ZoneTicket zoneTicket = new ZoneTicket();
	private LineTicket lineTicket = new LineTicket();
	private ZoneTicket ticketToBuy = new ZoneTicket();
	
	@Before
	public void setUp() {
		user.setUsername("user1");
		user.setPassword("1234");
		user.setRoles(Role.ROLE_CLIENT);
		user.setUserTypeDemo(UserTypeDemographic.STUDENT);
		
		zoneTicket.setId(1L);
		lineTicket.setId(2L);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.ONE_HOUR_PASS);
		lineTicket.setTicketTemporal(TicketTypeTemporal.ONE_HOUR_PASS);
		zoneTicket.setTransportType(VehicleType.BUS);
		lineTicket.setTransportType(VehicleType.BUS);
		zoneTicket.setUser(user);
		lineTicket.setUser(user);
		zoneTicket.setZone(new Zone(1L, "Zone I", true));
		zoneTicket.setActive(false);
		
		ticketToBuy.setTicketTemporal(TicketTypeTemporal.ONE_HOUR_PASS);
		ticketToBuy.setTransportType(VehicleType.BUS);
		ticketToBuy.setZone(new Zone(1L, "Zone I", true));
		
		Mockito.when(jwtServiceMocked.validate(TOKEN.substring(7))).thenReturn(user);
		Mockito.when(userServiceMocked.findByUsername("user1")).thenReturn(user);
		Mockito.when(ticketRepositoryMocked.findById(1L)).thenReturn(Optional.of(zoneTicket));
		Mockito.when(ticketRepositoryMocked.findById(2L)).thenReturn(Optional.of(lineTicket));
		Mockito.when(ticketRepositoryMocked.findById(-1L)).thenThrow(new DAOException("Ticket [id=-1L] cannot be found!"));
		Mockito.when(ticketRepositoryMocked.save(zoneTicket)).thenReturn(zoneTicket);
	}

	
	// ovo ni ne treba da bude ovde nego u UserService
	@Test
	public void getUserByToken_PASS_Test() {
		User found = ticketService.getUser(TOKEN);
		assertNotNull(found);
		assertEquals(user.getUsername(), found.getUsername());
	}
	
	@Test
	public void getTicketById_PASS_Test() {
		Ticket found = ticketService.findById(1L);
		assertNotNull(found);
		assertTrue(found instanceof ZoneTicket);
	}
	
	@Test(expected = DAOException.class)
	public void getTicketById_NotFound_Test() {
		ticketService.findById(-1L);
	}
	
	@Test
	@Transactional
	public void activateTicket_PASS_Test() {
		assertFalse(zoneTicket.isActive());
		Ticket ret = ticketService.activateTicket(zoneTicket);
		assertTrue(ret.isActive());
		assertNotNull(ret.getStartTime());
		assertNotNull(ret.getEndTime());
	}
	
	@Test
	@Transactional
	public void buyTicket_DocumentNotNeeded_PASS_Test() {
		Mockito.when(priceListServiceMocked.calculateTicketPrice(ticketToBuy)).thenReturn(500.00);
		Mockito.when(ticketRepositoryMocked.save(ticketToBuy)).thenReturn(ticketToBuy);
		
		
		assertNull(ticketToBuy.getUser());
		assertNotEquals(500.00, ticketToBuy.getPrice(), 0.0001);
		
		Ticket bought = ticketService.buyTicket(ticketToBuy, TOKEN);
		
		assertNotNull(ticketToBuy.getUser());
		assertEquals(500.00 ,ticketToBuy.getPrice(), 0.0001);
	}
	
	@Test(expected = InvalidInputDataException.class)
	@Transactional
	public void buyTicket_DocumentNotFound_Test() {
		ticketToBuy.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		user.setDocumentVerified(DocumentVerification.NO_DOCUMENT);
		
		// throws InvalidInputDataException - no document - cannot buy monthly ticket!
		ticketService.buyTicket(ticketToBuy, TOKEN);
	}
	
	@Test(expected = InvalidInputDataException.class)
	@Transactional
	public void buyTicket_DocumentVerificationPending_Test() {
		ticketToBuy.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		user.setDocumentVerified(DocumentVerification.PENDING);

		// throws InvalidInputDataException - document verification PENDING - cannot buy monthly ticket!
		ticketService.buyTicket(ticketToBuy, TOKEN);
	}
	
	@Test(expected = InvalidInputDataException.class)
	@Transactional
	public void buyTicket_DocumentRejected_Test() {
		ticketToBuy.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		user.setDocumentVerified(DocumentVerification.REJECTED);

		// throws InvalidInputDataException - document verification REJECTED - cannot buy monthly ticket!
		ticketService.buyTicket(ticketToBuy, TOKEN);
	}	
	
	@Test
	@Transactional
	public void buyTicket_DocumentApproved_PASS_Test() {
		Mockito.when(priceListServiceMocked.calculateTicketPrice(ticketToBuy)).thenReturn(500.00);
		Mockito.when(ticketRepositoryMocked.save(ticketToBuy)).thenReturn(ticketToBuy);
		user.setDocumentVerified(DocumentVerification.APPROVED);
		
		assertNull(ticketToBuy.getUser());
		assertNotEquals(500.00, ticketToBuy.getPrice(), 0.0001);
		
		ticketService.buyTicket(ticketToBuy, TOKEN);
		
		assertNotNull(ticketToBuy.getUser());
		assertEquals(500.00 ,ticketToBuy.getPrice(), 0.0001);
	}
	
}