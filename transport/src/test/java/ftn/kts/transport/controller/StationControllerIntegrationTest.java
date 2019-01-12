package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.exception.StationNotFoundException;
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
				restTemplate.getForEntity("/station/all", StationDTO[].class);
		
		StationDTO[] rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(rez);
	}
	
	@Test
	public void addTestOK() {
		int size = stationService.findAll().size();
		
		StationDTO entity= new StationDTO(Long.valueOf(44), "Jevrejska 55", "Centar2");
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/add", entity, StationDTO.class);
	
		responseEntity.getBody();

		assertEquals(size + 1, stationService.findAll().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest1() {
		
		StationDTO entity= new StationDTO(Long.valueOf(44), null, "Centar2");
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/add", entity, StationDTO.class);
	
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest2() {
		
		StationDTO entity= new StationDTO(Long.valueOf(44), "Jevrejska", null);
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/add", entity, StationDTO.class);
	
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteStationTestOK() {
		
		Station station = stationService.save(new Station(Long.valueOf(45), "Jevrejska 13", "Centar", true));
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/delete/" + station.getId(),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, stationService.findAll().size());
	}
	
	@Test
	public void deleteStationTestBadRequest() {
		
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/delete/" + Long.valueOf(1),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, stationService.findAll().size());
	}
	
	@Test
	public void deleteStationTestNotFound() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/delete/" + Long.valueOf(75),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateZoneTestOK() {
		
		Station station = stationService.findById(Long.valueOf(3));
		
		station.setName("Futoska");
		station.setLineSet(null);
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/update", new StationDTO(station), StationDTO.class);
		
		StationDTO rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Futoska", rez.getName());
	}

	@Test(expected=StationNotFoundException.class)
	public void updateZoneTest() {
		
		Station station = stationService.findById(Long.valueOf(53));
		
		station.setName("Futoska");
		station.setLineSet(null);
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/update", new StationDTO(station), StationDTO.class);

	}


}
