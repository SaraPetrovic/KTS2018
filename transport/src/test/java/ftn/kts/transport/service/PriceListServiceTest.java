package ftn.kts.transport.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.PriceListRepository;
import ftn.kts.transport.services.PriceListService;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PriceListServiceTest {

	@Autowired
	private PriceListService service;
	
	@MockBean
	private PriceListRepository plRepoMocked;
	
	@MockBean
	private ZoneService zoneServiceMocked;
	
	private PriceList validPriceList = new PriceList();
	private PriceList invalidPriceList = new PriceList();
	private PriceList invalidPricesPL = new PriceList();
	
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
	}
	
	
	@Test
	public void checkData_PASS_Test() {
		boolean check = service.checkData(validPriceList);
		assertTrue(check);
	}
	
	@Test(expected = InvalidInputDataException.class)
	public void checkData_throwsInvalidInput_Test() {
		service.checkData(invalidPriceList);
	}
	
	@Test(expected = DAOException.class)
	public void checkData_throwsDAO_Test() {
		service.checkData(invalidPricesPL);
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
}
