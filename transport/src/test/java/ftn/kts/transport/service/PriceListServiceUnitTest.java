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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.RouteTicket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;
import ftn.kts.transport.repositories.PriceListRepository;
import ftn.kts.transport.services.LineService;
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
	private LineService lineServiceMocked;
	
	@MockBean
	private ZoneService zoneServiceMocked;
	
	@SpyBean
	private PriceListService spyService;
	
	private PriceList validPriceList = new PriceList();
	private PriceList invalidPriceList = new PriceList();
	private PriceList invalidPricesPL = new PriceList();
	private User user = new User();
	private ZoneTicket zoneTicket = new ZoneTicket();
	private LineTicket lineTicket = new LineTicket();
	private RouteTicket routeTicket = new RouteTicket();
	private Line line = new Line();
	private Zone zone = new Zone();
	private Route route = new Route();
	
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
		validPriceList.setOneHourCoeffitient(3);
		
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
		
		line.setId(1L);
		line.setName("7A");
		line.setActive(true);
		line.setDuration(10000);
		line.setTransportType(VehicleType.BUS);
		
		zone.setId(1L);
		zone.setActive(true);
		zone.setName("Zone I");
		zone.setSubZone(null);
		
		route.setId(1L);
		route.setLine(line);
		
		Mockito.when(zoneServiceMocked.getZoneForLine(line)).thenReturn(zone);
				
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
		zoneTicket.setZone(zone);
		lineTicket.setLine(line);
		
		routeTicket.setTicketTemporal(TicketTypeTemporal.ONE_TIME_PASS);
		routeTicket.setTransportType(VehicleType.BUS);
		routeTicket.setUser(user);
		routeTicket.setRoute(route);
		
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
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.empty());
		service.activatePriceList(2L);
	}
	
	@Test(expected = InvalidInputDataException.class)
	public void activatePriceList_throwsInvalidInputTest() {
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(invalidPricesPL));
		service.activatePriceList(3L);
	}
	
	@Transactional
	@Test
	public void activatePriceList_ActiveAlreadyExists_PASS_Test() {
		PriceList alreadyActive = new PriceList();
		alreadyActive.setId(5L);
		alreadyActive.setLineDiscount(0.5);
		alreadyActive.setMonthlyCoeffitient(20);
		alreadyActive.setOneTimePrices(validPriceList.getOneTimePrices());
		alreadyActive.setSeniorDiscount(0.7);
		alreadyActive.setStudentDiscount(0.8);
		alreadyActive.setYearlyCoeffitient(200);
		alreadyActive.setOneHourCoeffitient(3);
		
		Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(alreadyActive));
		Mockito.when(plRepoMocked.findById(5L)).thenReturn(Optional.of(alreadyActive));
		Mockito.when(plRepoMocked.save(alreadyActive)).thenReturn(alreadyActive);
		
		boolean ret = service.activatePriceList(1L);
		assertTrue(ret);
		
	}
	
	@Transactional
	@Test(expected = DAOException.class)
	public void calculatePrice_NoActivePriceList_Test() {
		Mockito.doReturn(null).when(spyService).getActivePriceList();
		spyService.calculateTicketPrice(routeTicket);
	}

	
	@Test
	public void calculatePriceRouteTicket_OneTime_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		double calculatedPrice = spyService.calculateTicketPrice(routeTicket);
		assertEquals(100.00, calculatedPrice, 0.001);
	}
	
	@Test
	public void calculatePriceZoneTicket_OneHour_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = one_hour --> one_time*one_hour_coeff (3)
		assertEquals(100.00*3, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Monthly_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = MONTHLY --> nova cena
		assertEquals(100.00*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceLineTicket_Monthly_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		lineTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(lineTicket);
		// user je obican, ticket type = MONTHLY, lineTicket disc = 0.5 --> nova cena
		assertEquals(100.00*20*0.5, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceLineTicket_Yearly_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		lineTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(lineTicket);
		// user je obican, ticket type = MONTHLY, lineTicket disc = 0.5 --> nova cena
		assertEquals(100.00*200*0.5, calculatedPrice, 0.001);
	}

	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Yearly_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user je obican, ticket type = YEARLY --> nova cena
		assertEquals(100.00*200, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_OneHour_Student_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = ONE_HOUR --> ONE_TIME * ONE_HOUR_COEFF (3)
		assertEquals(100.00*0.8*3, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_OneHour_Senior_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = ONE_TIME --> ONE_TIME*ONE_HOUR(3)
		assertEquals(100.00*0.7*3, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Monthly_Student_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = MONTHLY --> cena nova
		assertEquals(100.00*0.8*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Monthly_Senior_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.MONTHLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = MONTHLY --> cena nova
		assertEquals(100.00*0.7*20, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Yearly_Student_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.STUDENT);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = STUDENT, ticket type = YEARLY --> cena nova
		assertEquals(100.00*0.8*200, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void calculatePriceZoneTicket_Yearly_Senior_PASS_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		zoneTicket.getUser().setUserTypeDemo(UserTypeDemographic.SENIOR);
		zoneTicket.setTicketTemporal(TicketTypeTemporal.YEARLY_PASS);
		double calculatedPrice = spyService.calculateTicketPrice(zoneTicket);
		// user = SENIOR, ticket type = YEARLY --> cena nova
		assertEquals(100.00*0.7*200, calculatedPrice, 0.001);
	}
	
	@Transactional
	@Test
	public void getActivePriceList_PASS_Test() {
		validPriceList.setActive(true);
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.of(validPriceList));
		Mockito.doReturn(validPriceList).when(spyService).getActivePriceList();
		
		PriceList found = spyService.getActivePriceList();
		assertNotNull(found);
		assertTrue(found.isActive());
		
	}
	
	@Test
	public void getActivePriceList_NoActive_Test() {
		//Mockito.when(plRepoMocked.findByActive(true)).thenReturn(Optional.empty());
		Mockito.doReturn(null).when(spyService).getActivePriceList();
		
		PriceList notFound = spyService.getActivePriceList();
		assertNull(notFound);
	}
}
