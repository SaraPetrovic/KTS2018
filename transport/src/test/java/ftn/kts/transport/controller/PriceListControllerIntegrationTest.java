package ftn.kts.transport.controller;

import static ftn.kts.transport.constants.UserConstants.DB_USER_ADMIN;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.model.PriceList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class PriceListControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private PriceListDTO plDTO = new PriceListDTO();
	
	@Before
	public void setup() {
		plDTO.setOnehourCoeff(3);
		plDTO.setLineDiscount(0.5);
		plDTO.setStudentDiscount(0.8);
		plDTO.setSeniorDiscount(0.7);
		plDTO.setMonthlyCoeff(17);
		plDTO.setYearlyCoeff(170);
		plDTO.setStartDate(new Date());
	}
	
	@Test
	public void addNewPricelist_PASS_Test() {
		HashMap<Long, Double> zonePrices = new HashMap<Long, Double>();
		zonePrices.put(1L, 100.00);

		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(1L, 100.00);
		plDTO.setZonePrices(zonePrices);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		PriceList ret = response.getBody();
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(0.5, ret.getLineDiscount(), 0.001);
		assertEquals(0.8, ret.getStudentDiscount(), 0.001);
		assertEquals(0.7, ret.getSeniorDiscount(), 0.001);
		assertEquals(17, ret.getMonthlyCoeffitient(), 0.001);
		assertEquals(170, ret.getYearlyCoeffitient(), 0.001);
		assertNotNull(ret.getStartDateTime());
		assertNotNull(ret.getOneTimePrices());
		assertEquals(1, ret.getOneTimePrices().size());
		assertEquals(3, ret.getOneHourCoeffitient(), 0.001);

	}
	
	@Test
	public void addNewPricelist_ZoneNotFound_Test() {
		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(-1L, 100.00);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	public void addNewPricelist_InvalidParameter_Test() {
		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(1L, 100.00);
		plDTO.setMonthlyCoeff(-1);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void addNewPricelist_Unauthorized_Test() {
		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(1L, 100.00);
		plDTO.setMonthlyCoeff(15);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
	}
	
	@Test
	public void addNewPricelist_InvalidToken_Test() {
		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(1L, 100.00);
		plDTO.setMonthlyCoeff(15);
		
		String invalidToken = "asfas invalid token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}
	
	@Test
	public void addNewPricelist_TokenMissing_Test() {
		plDTO.setZonePrices(new HashMap<Long, Double>());
		plDTO.getZonePrices().put(1L, 100.00);
		plDTO.setMonthlyCoeff(15);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<PriceListDTO> entity = new HttpEntity<PriceListDTO>(plDTO, headers);
		
		ResponseEntity<PriceList> response = restTemplate.exchange("/rest/priceList", HttpMethod.POST, entity, PriceList.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
}
