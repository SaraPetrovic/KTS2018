package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Ignore;
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


import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.model.Point;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class StationControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private StationService stationService;
	

	@Test
	public void getAllTest() {
		ResponseEntity<StationDTO[]> responseEntity = 
				restTemplate.getForEntity("/station", StationDTO[].class);
		
		StationDTO[] rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(rez);
	}

	@Test
	public void addTestOK() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>(new StationDTO(Long.valueOf(44), "Jevrejska 55", "Centar2", new Point(10,10)), headers);
		
		ResponseEntity<StationDTO> responseEntity =
				restTemplate.exchange("/rest/station", HttpMethod.POST,
						entity, StationDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestForbidden() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>(new StationDTO(Long.valueOf(44), "Jevrejska 55", "Centar2", new Point(10,10)), headers);
		
		ResponseEntity<StationDTO> responseEntity =
				restTemplate.exchange("/rest/station", HttpMethod.POST,
						entity, StationDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest1() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>(new StationDTO(Long.valueOf(44), null, "Centar2", new Point(10,10)), headers);
		
		ResponseEntity<StationDTO> responseEntity =
				restTemplate.exchange("/rest/station", HttpMethod.POST,
						entity, StationDTO.class);
	
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest2() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>( new StationDTO(Long.valueOf(44), "Jevrejska", null, new Point(10,10)), headers);
		
		ResponseEntity<StationDTO> responseEntity =
				restTemplate.exchange("/rest/station", HttpMethod.POST,
						entity, StationDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void addTestBadRequest3() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>( new StationDTO(Long.valueOf(44), "Jevrejska", null, new Point(0,0)), headers);
		
		ResponseEntity<StationDTO> responseEntity =
				restTemplate.exchange("/rest/station", HttpMethod.POST,
						entity, StationDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void deleteStationTestOK() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.save(new Station(Long.valueOf(45), "Jevrejska 13", "Centar", true));
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/station/" + station.getId(),
						HttpMethod.DELETE, new HttpEntity<Object>(null, headers), Void.class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, stationService.findAll().size());
	}
	
	@Test
	public void deleteStationTestForbidden() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.save(new Station(Long.valueOf(45), "Jevrejska 13", "Centar", true));
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/station/" + station.getId(),
						HttpMethod.DELETE, new HttpEntity<Object>(null, headers), Void.class);
	
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void deleteStationTestBadRequest() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/station/" + Long.valueOf(1),
						HttpMethod.DELETE, new HttpEntity<Object>(null, headers), Void.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, stationService.findAll().size());
	}

	@Test
	public void deleteStationTestNotFound() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/station/" + Long.valueOf(75),
						HttpMethod.DELETE, new HttpEntity<Object>(null, headers), Void.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateStationTestOK() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.findById(Long.valueOf(3));
		
		station.setName("Futoska");
		station.setLineSet(null);
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/rest/station/" + station.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(station), headers),
	            		StationDTO.class);

		StationDTO rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Futoska", rez.getName());
	}

	@Test
	public void updateStationTestForbidden() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.findById(Long.valueOf(3));
		
		station.setName("Futoska");
		station.setLineSet(null);
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/rest/station/" + station.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(station), headers),
	            		StationDTO.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test(expected=StationNotFoundException.class)
	public void updateStationTest() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.findById(Long.valueOf(53));
		
		
        restTemplate.exchange("/rest/station/" + station.getId(), HttpMethod.PUT, 
        		new HttpEntity<StationDTO>(new StationDTO(station), headers),
        		StationDTO.class);
		
	}
	
	@Test
	public void updateStationTestBadREquest() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Station station = stationService.findById(Long.valueOf(2));
		station.setName("");
		station.setAddress(null);
		
		StationDTO dto = new StationDTO(Long.valueOf(2), null, "", new Point(10,10));
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/rest/station/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(dto, headers),
	            		StationDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
}
