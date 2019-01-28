package ftn.kts.transport.controller;

import static ftn.kts.transport.constants.LineConstants.DB_LINES_COUNT;
import static ftn.kts.transport.constants.LineConstants.DB_LINE_NAME1;
import static ftn.kts.transport.constants.LineConstants.DB_STATIONID_INVALID;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE_INVALID;
import static ftn.kts.transport.constants.UserConstants.DB_USER_ADMIN;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.services.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LineControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private LineRepository lineRepository;
	
	@Test
	public void a1getLinesTest() {
		lineService.getAllLines().size();
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line", List.class);
		List<Line> found = responseEntity.getBody();
		assertNotNull(found);
		assertEquals(DB_LINES_COUNT, found.size());
	}
	
	@Test
	public void addLine_PASS_Test() {
		
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, 1L);
		LineDTO dto = new LineDTO("1A - Nova", stations, DB_TRANSPORT_TYPE);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);
		
		int countBefore = lineRepository.findAll().size();
		
		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);

		Line ret = response.getBody();
		
		int countAfter = lineRepository.findAll().size();
		
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(dto.getName(), ret.getName());
		assertEquals(dto.getVehicleType(), ret.getTransportType().ordinal());
		
		assertEquals(countBefore + 1, countAfter);
	}
	

	@Test
	public void addLine_AlreadyExists_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, 1L);
		LineDTO dto = new LineDTO(DB_LINE_NAME1, stations, DB_TRANSPORT_TYPE);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);
		
		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	

	@Test
	public void addLine_InvalidTransportType_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, 1L);
		LineDTO dto = new LineDTO("Nova", stations, DB_TRANSPORT_TYPE_INVALID);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);
		
		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void addLine_InvalidStations_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, DB_STATIONID_INVALID);
		LineDTO dto = new LineDTO("Nova linija", stations, DB_TRANSPORT_TYPE);
		
		int countBefore = lineService.getAllLines().size();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		int countAfter = lineService.getAllLines().size();
		assertEquals(countBefore, countAfter);
	}
	
	@Test
	public void addLine_InvalidToken_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, DB_STATIONID_INVALID);
		LineDTO dto = new LineDTO("Nova linija", stations, DB_TRANSPORT_TYPE);
		String invalidToken = "ABC invalid token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void addLine_Unauthorized_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, DB_STATIONID_INVALID);
		LineDTO dto = new LineDTO("Nova linija", stations, DB_TRANSPORT_TYPE);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

	}
	
	
}
