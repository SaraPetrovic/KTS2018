package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.dtos.ZoneDTO;
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
				restTemplate.getForEntity("/station", StationDTO[].class);
		
		StationDTO[] rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(rez);
	}
	
	@Test
	public void addTestOK() {
		int size = stationService.findAll().size();
		
		StationDTO entity= new StationDTO(Long.valueOf(44), "Jevrejska 55", "Centar2");
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station", entity, StationDTO.class);
	
		responseEntity.getBody();

		assertEquals(size + 1, stationService.findAll().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest1() {
		
		StationDTO entity= new StationDTO(Long.valueOf(44), null, "Centar2");
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station", entity, StationDTO.class);
	
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest2() {
		
		StationDTO entity= new StationDTO(Long.valueOf(44), "Jevrejska", null);
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station", entity, StationDTO.class);
	
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteStationTestOK() {
		
		Station station = stationService.save(new Station(Long.valueOf(45), "Jevrejska 13", "Centar", true));
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + station.getId(),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, stationService.findAll().size());
	}
	
	@Test
	public void deleteStationTestBadRequest() {
		
		int size = stationService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + Long.valueOf(1),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, stationService.findAll().size());
	}
	
	@Test
	public void deleteStationTestNotFound() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + Long.valueOf(75),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateStationTestOK() {
		
		Station station = stationService.findById(Long.valueOf(3));
		
		station.setName("Futoska");
		station.setLineSet(null);
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + station.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(station)),
	            		StationDTO.class);

		StationDTO rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Futoska", rez.getName());
	}

	@Test(expected=StationNotFoundException.class)
	public void updateStationTest() {
		
		Station station = stationService.findById(Long.valueOf(53));
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + station.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(station)),
	            		StationDTO.class);
	}
	
	@Test
	public void updateStationTestBadREquest() {
		
		Station station = stationService.findById(Long.valueOf(2));
		station.setName("");
		station.setAddress(null);
		
		StationDTO dto = new StationDTO(Long.valueOf(2), null, "");
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(dto),
	            		StationDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

}
