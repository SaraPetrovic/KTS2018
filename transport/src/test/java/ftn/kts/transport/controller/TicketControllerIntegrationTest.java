package ftn.kts.transport.controller;

import static ftn.kts.transport.constants.UserConstants.DB_USER_ADMIN;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT_NODOCUMENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import ftn.kts.transport.dtos.MyTicketDTO;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketTypeTemporal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	private TicketDTO ticketDTO = new TicketDTO();
	
	@Before
	public void setup() {
		ticketDTO.setZoneId(1L);
		ticketDTO.setTransportType(0);
		ticketDTO.setTicketTemporal(0);
		ticketDTO.setRouteId(null);
		ticketDTO.setLineId(null);
	}
	
	
	@Test
	public void a1activateTicket_PASS_Test() {
				
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void activateTicket_TicketNotFound_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/-1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void a2activateTicket_TicketAlreadyActivated_Test() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void activateTicket_Unauthorized_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
	}
	
	@Test
	public void activateTicket_InvalidToken_Test() {
		String invalidToken = "invalid token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
	}
	
	@Test
	public void activateTicket_MissingToken_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/activate/1", HttpMethod.PUT, entity, MyTicketDTO.class);
		
		
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	public void buyTicket_PASS_Test() {
		ticketDTO.setTransportType(0);
		ticketDTO.setZoneId(1L);
		ticketDTO.setTicketTemporal(0);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		MyTicketDTO ret = response.getBody();
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ticketDTO.getTicketTemporal(), ret.getTicketTemporal().ordinal());
		assertEquals(ticketDTO.getTransportType(), ret.getTransportType().ordinal());
		assertEquals(ticketDTO.getZoneId(), ret.getZone().getId());	
	}
	
	@Test
	public void buyTicket_InvalidTranposrt_Test() {
		ticketDTO.setTransportType(-1);
		ticketDTO.setZoneId(1L);
		ticketDTO.setTicketTemporal(0);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		MyTicketDTO ret = response.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void buyTicket_MonthlyTicket_DocumentNotVerified_Test() {
		ticketDTO.setTransportType(0);
		ticketDTO.setTicketTemporal(1);
		ticketDTO.setZoneId(1L);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT_NODOCUMENT);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void buyTicket_ZoneNotFound_Test() {
		ticketDTO.setTransportType(0);
		ticketDTO.setTicketTemporal(0);
		ticketDTO.setZoneId(-1L);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
			
	}
	
	@Test
	public void buyTicket_Unauthorized_Test() {
		ticketDTO.setTransportType(0);
		ticketDTO.setZoneId(1L);
		ticketDTO.setTicketTemporal(0);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void buyTicket_InvalidToken_Test() {
		
		String invalidToken = "invalid token";
		ticketDTO.setTransportType(0);
		ticketDTO.setZoneId(1L);
		ticketDTO.setTicketTemporal(0);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void buyTicket_MissingToken_Test() {
		ticketDTO.setTransportType(0);
		ticketDTO.setZoneId(1L);
		ticketDTO.setTicketTemporal(0);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<TicketDTO> entity = new HttpEntity<TicketDTO>(ticketDTO, headers);
		
		ResponseEntity<MyTicketDTO> response = restTemplate.exchange("/rest/ticket/buy", HttpMethod.POST, entity, MyTicketDTO.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
	
	
	@Test
	public void getAllTicketTypes_PASS_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<List> response = restTemplate.exchange("/ticket/types", HttpMethod.GET, entity, List.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4, response.getBody().size());
		assertEquals(TicketTypeTemporal.ONE_TIME_PASS.toString(), response.getBody().get(3));
		assertEquals(TicketTypeTemporal.ONE_HOUR_PASS.toString(), response.getBody().get(0));
		assertEquals(TicketTypeTemporal.MONTHLY_PASS.toString(), response.getBody().get(1));
		assertEquals(TicketTypeTemporal.YEARLY_PASS.toString(), response.getBody().get(2));
	}
	
	@Test
	public void getMyTickets_PASS_Test() {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		ResponseEntity<ArrayList> response = restTemplate.exchange("/rest/ticket/me", HttpMethod.GET, entity, ArrayList.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
	}
	
	
	
	
}
