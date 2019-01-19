package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;
import ftn.kts.transport.repositories.PriceListRepository;
import ftn.kts.transport.services.PriceListService;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class PriceListServiceUnitTest {

	@Autowired
	private PriceListService service;
	
	@MockBean
	private PriceListRepository plRepoMocked;
	
	@MockBean
	private ZoneService zoneServiceMocked;
	
	private PriceList validPriceList = new PriceList();
	private PriceList invalidPriceList = new PriceList();
	private PriceList invalidPricesPL = new PriceList();
	private User user = new User();
	private ZoneTicket zoneTicket = new ZoneTicket();
	private LineTicket lineTicket = new LineTicket();
	
	@Before
	public void setUp() {
		Map<Long, Double> prices = new HashMap<Long, Double>();
		Map<Long, Double> invalidPrices = new HashMap<Long, Double>();
		prices.put(1L, 100.00);
		prices.put(2L, 200.00);
		invalidPrices.put(3L, 300.00);
		validPriceList.setId(1L);
		validPriceList.setLineDiscount(0.5);
		validPriceList.setMonthlyCoeffitient(20);
		validPriceList.setOneTimePrices(prices);
		validPriceList.setSeniorDiscount(0.7);
		validPriceList.setStudentDiscount(0.8);
		validPriceList.setYearlyCoeffitient(200);
		
		invalidPriceList.setId(2L);
		invalidPriceList.setLineDiscount(-0.5);
		invalidPriceList.setMonthlyCoeffitient(20);
		invalidPriceList.setOneTimePrices(prices);
		invalidPriceList.setSeniorDiscount(0.7);
		invalidPriceList.setStudentDiscount(0.8);
		invalidPriceList.setYearlyCoeffitient(200);
		
		invalidPricesPL.setId(3L);
		invalidPricesPL.setOneTimePrices(invalidPrices);
		invalidPricesPL.setActive(true);
		
		Optional<PriceList> optValid = Optional.of(validPriceList);
		Optional<PriceList> optInvalid2 = Optional.of(invalidPricesPL);
		
		Mockito.when(zoneServiceMocked.findById(1L)).thenReturn(new Zone(1L, "Zone I", true));
		Mockito.when(zoneServiceMocked.findById(2L)).thenReturn(new Zone(2L, "Zone II", true));
		Mockito.when(zoneServiceMocked.findById(3L)).thenThrow(new DAOException("Zone not found!", HttpStatus.NOT_FOUND));
		
		Mockito.when(plRepoMocked.findById(1L)).thenReturn(optValid);
		Mockito.when(plRepoMocked.findById(2L)).thenThrow(new DAOException("Price List [id=2] not found!", HttpStatus.NOT_FOUND));
		Mockito.when(plRepoMocked.findById(3L)).thenReturn(optInvalid2);
		Mockito.when(plRepoMocked.save(validPriceList)).thenReturn(validPriceList);
		
		
		user.setUserTypeDemo(UserTypeDemographic.NORMAL);
		
		zoneTicket.setTicketTemporal(TicketTypeTemporal.ONE_HOUR_PASS);
		lineTicket.setTicketTemporal(TicketTypeTemporal.ONE_HOUR_PASS);
		zoneTicket.setTransportType(VehicleType.BUS);
		lineTicket.setTransportType(VehicleType.BUS);
		zoneTicket.setUser(user);
		lineTicket.setUser(user);
		zoneTicket.setZone(new Zone(1L, "Zone I", true));
		
		
		
	}
	
	

	@Test
	public void addPriceList_PASS_Test() {
		PriceList added = service.addPriceList(validPriceList);
		assertNotNull(added);
		assertEquals(validPriceList.getId(), added.getId());
	}
	
	
	@Test
	public void activatePriceList_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.empty());
		boolean check = service.activatePriceList(1L);
		assertTrue(check);
	}
	
	@Test(expected = DAOException.class)
	public void activatePriceList_throwsDAO_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.empty());
		service.activatePriceList(2L);
	}
	
	@Test(expected = InvalidInputDataException.class)
	public void activatePriceList_throwsInvalidInputTest() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(invalidPricesPL));
		service.activatePriceList(3L);
	}
	
	@Test
	public void calculatePriceZoneTicket_OneTime_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = one_time --> cena ostaje ista
		assertEquals(100.00, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Monthly_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = MONTHLY --> nova cena
		assertEquals(100.00*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Yearly_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = YEARLY --> nova cena
		assertEquals(100.00*200, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_OneTime_Student_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = ONE_TIME --> cena nova
		assertEquals(100.00*0.8, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_OneTime_Senior_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = ONE_TIME --> cena nova
		assertEquals(100.00*0.7, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Monthly_Student_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = MONTHLY --> cena nova
		assertEquals(100.00*0.8*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZone_Monthly_Senior_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = MONTHLY --> cena nova
		assertEquals(100.00*0.7*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Yearly_Student_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = YEARLY --> cena nova
		assertEquals(100.00*0.8*200, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZone_Yearly_Senior_PASS_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = service.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = YEARLY --> cena nova
		assertEquals(100.00*0.7*200, calculatedPrice, 0.001);
	}
	
	
	@Transactional
	@Test
	public void getActivePriceList_PASS_Test() {
		validPriceList.setActive(true);
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		PriceList found = service.getActivePriceList();
		assertNotNull(found);
		assertTrue(found.isActive());
		
	}
	
	@Transactional
	@Test
	public void getActivePriceList_NoActive_Test() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.empty());
		PriceList notFound = service.getActivePriceList();
		assertNull(notFound);
	}
}
