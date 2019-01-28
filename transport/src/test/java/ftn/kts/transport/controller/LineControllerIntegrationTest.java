package ftn.kts.transport.controller;

import static ftn.kts.transport.constants.LineConstants.DB_BUS_LINES_COUNT;
import static ftn.kts.transport.constants.LineConstants.DB_LINEID1;
import static ftn.kts.transport.constants.LineConstants.DB_LINEID_INVALID;
import static ftn.kts.transport.constants.LineConstants.DB_LINES_BY_ZONE2_COUNT;
import static ftn.kts.transport.constants.LineConstants.DB_LINES_COUNT;
import static ftn.kts.transport.constants.LineConstants.DB_LINE_NAME1;
import static ftn.kts.transport.constants.LineConstants.DB_STATIONID_INVALID;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE_INVALID;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE_INVALID_STRING;
import static ftn.kts.transport.constants.LineConstants.DB_TRANSPORT_TYPE_STRING;
import static ftn.kts.transport.constants.UserConstants.DB_USER_ADMIN;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LineControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	
	@Test
	public void a1getLinesTest() {
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line", List.class);
		List<Line> found = responseEntity.getBody();
		assertNotNull(found);
		assertEquals(DB_LINES_COUNT, found.size());
	}
	
	
	@Test
	public void a1getLinesByType_PASS_Test() {

		ResponseEntity<List> responseEntity =
				restTemplate.getForEntity("/line/" + DB_TRANSPORT_TYPE_STRING, List.class);
		List<Line> found = responseEntity.getBody();
		assertNotNull(found);
		assertEquals(DB_BUS_LINES_COUNT, found.size());
	}
	
	@Test
	public void getLinesByTape_InvalidTransportType_Test() {
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line/" + DB_TRANSPORT_TYPE_INVALID_STRING, List.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void getLinesByZoneAndTransportType_PASS_Test() {
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line/zone/3/" + DB_TRANSPORT_TYPE_STRING, List.class);
		assertNotNull(responseEntity.getBody());
		assertEquals(DB_LINES_BY_ZONE2_COUNT, responseEntity.getBody().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	public void getLinesByZoneAndTransportType_ZoneNotFound() {
		ResponseEntity responseEntity = 
				restTemplate.getForEntity("/line/zone/-1/" + DB_TRANSPORT_TYPE_STRING, null);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}
	
	@Test
	public void getLinesByZoneAndTransportType_InvalidTransportType() {
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line/zone/1/" + DB_TRANSPORT_TYPE_INVALID_STRING, List.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
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
		
		
		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);

		Line ret = response.getBody();
		
		
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(dto.getName(), ret.getName());
		assertEquals(dto.getVehicleType(), ret.getTransportType().ordinal());
		
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
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
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
	
	@Test
	public void addLine_MissingToken_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, DB_STATIONID_INVALID);
		LineDTO dto = new LineDTO("Nova linija", stations, DB_TRANSPORT_TYPE);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line", HttpMethod.POST, entity, Line.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
	
	
	@Test
	public void updateLine_PASS_Test() {
		LineDTO dto = new LineDTO();
		Set<String> path = new HashSet<String>();
		path.add("novi path");
		dto.setDescription("new description");
		dto.setName("newww name");
		dto.setVehicleType(0);
		dto.setStreetPath(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/7", HttpMethod.PUT, entity, Line.class);

		Line body = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(body);
		assertEquals("new description", body.getDescription());
		assertEquals("newww name", body.getName());
		assertEquals(1, body.getStreetPath().size());
		assertEquals(0, body.getTransportType().ordinal());
		

	}
	
	@Test
	public void updateLine_LineNotFound_Test() {
		LineDTO dto = new LineDTO();
		Set<String> path = new HashSet<String>();
		path.add("novi path");
		dto.setDescription("new description");
		dto.setName("newww name");
		dto.setVehicleType(0);
		dto.setStreetPath(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID_INVALID, HttpMethod.PUT, entity, Line.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void updateLine_Unauthorized_Test() {
		LineDTO dto = new LineDTO();
		Set<String> path = new HashSet<String>();
		path.add("novi path");
		dto.setDescription("new description");
		dto.setName("newww name");
		dto.setVehicleType(0);
		dto.setStreetPath(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID_INVALID, HttpMethod.PUT, entity, Line.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void updateLine_InvalidToken_Test() {
		LineDTO dto = new LineDTO();
		Set<String> path = new HashSet<String>();
		path.add("novi path");
		dto.setDescription("new description");
		dto.setName("newww name");
		dto.setVehicleType(0);
		dto.setStreetPath(path);
		
		String invalidToken = "ABC invalid token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID_INVALID, HttpMethod.PUT, entity, Line.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void updateLine_MissingToken_Test() {
		LineDTO dto = new LineDTO();
		Set<String> path = new HashSet<String>();
		path.add("novi path");
		dto.setDescription("new description");
		dto.setName("newww name");
		dto.setVehicleType(0);
		dto.setStreetPath(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<LineDTO> entity = new HttpEntity<LineDTO>(dto, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID_INVALID, HttpMethod.PUT, entity, Line.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void deleteLine_PASS_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/6", HttpMethod.DELETE, entity, Line.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isActive());

	}
	
	@Test
	public void deleteLine_LineNotFound_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID_INVALID, HttpMethod.DELETE, entity, Line.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

	}

	@Test
	public void deleteLine_Unauthorized_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID1, HttpMethod.DELETE, entity, Line.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void deleteLine_InvalidToken_Test() {
		String invalidToken = "invalidd";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);


		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID1, HttpMethod.DELETE, entity, Line.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void deleteLine_MissingToken_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");

		ResponseEntity<Line> response = restTemplate.exchange("/rest/line/" + DB_LINEID1, HttpMethod.DELETE, null, Line.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

		
	}
	

	

	
}
