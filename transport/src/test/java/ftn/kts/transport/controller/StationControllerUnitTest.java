package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class StationControllerUnitTest {

	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private StationService stationService;
	
	public StationDTO dtoStation;
	public Station s3;
	
	@Before
	public void setUp() {
		Station s1 = new Station(Long.valueOf(1), "Bul. Oslobodjenja 1", "Gl. stanica", null, true);
		Station s2 = new Station(Long.valueOf(2), "Bul. Oslobodjenja 20", "Bul. Oslobodjenja - Kralja Petra I", null, true);
		s3 = new Station(Long.valueOf(3), "Bul. Oslobodjenja 56", "Bul. Oslobodjenja - Futoska", null, true);
		Station s4 = new Station(Long.valueOf(4), "Jevrejska 2", "Jevrejska - Bul. Oslobodjenja", null, true);
		
		List<Station> stations = new ArrayList<Station>();
		stations.add(s1);
		stations.add(s2);
		stations.add(s3);
		stations.add(s4);
		
		dtoStation = new StationDTO(s3);
		Station s = stationService.fromDtoToStation(dtoStation);
		Mockito.when(stationService.findAll()).thenReturn(stations);
		Mockito.when(stationService.save(s)).thenReturn(s);
		Mockito.when(stationService.delete(s1.getId())).thenReturn(true);
		Mockito.when(stationService.delete(Long.valueOf(99))).thenThrow(StationNotFoundException.class);
		
		Set<LineAndStation> lines = new HashSet<LineAndStation>();
		LineAndStation ls = new LineAndStation();
		lines.add(ls);
		Station s5 = new Station(Long.valueOf(98), "Jevrejska 2", "Jevrejska - Bul. Oslobodjenja", lines, true);
		
		Mockito.when(stationService.delete(s5.getId())).thenThrow(new DAOException("", HttpStatus.BAD_REQUEST));
		
		Mockito.when(stationService.findById(s3.getId())).thenReturn(s3);
	}
	
	@Test
	public void getAllTest() {
		ResponseEntity<StationDTO[]> responseEntity = 
				restTemplate.getForEntity("/station", StationDTO[].class);
		
		StationDTO[] rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(rez);
		assertEquals(4, rez.length);
	}
	
	@Ignore
	@Test
	public void addTestOK() {
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station", dtoStation, StationDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void addTestBadRequest() {
		StationDTO dto = new StationDTO(Long.valueOf(5), null, null, null);
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station", dto, StationDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteStationTestOK() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + Long.valueOf(1),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteStationTestBadRequest() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + Long.valueOf(98),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteStationTestNotFound() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/station/" + Long.valueOf(99),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateStationTestOK() {
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + Long.valueOf(3), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(Long.valueOf(3), "Bul. Oslobodjenja 56", "Bul. Oslobodjenja - Al zgrada", null)),
	            		StationDTO.class);
		
		StationDTO rez = responseEntity.getBody();

		assertNotNull(rez);
		assertEquals("Bul. Oslobodjenja - Al zgrada", rez.getName());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateStationTestNotFound() {
		
		Station s = new Station(Long.valueOf(53), "Adresa", "Ime", true);
		Mockito.when(stationService.findById(Long.valueOf(53))).thenThrow(StationNotFoundException.class);
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + Long.valueOf(53), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(s)),
	            		StationDTO.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateStationTestBadRequest() {
		
		Station s = new Station(Long.valueOf(53), "", "", true);
		Mockito.when(stationService.findById(Long.valueOf(53))).thenReturn(s);
		
		ResponseEntity<StationDTO> responseEntity =
	            restTemplate.exchange("/station/" + Long.valueOf(3), HttpMethod.PUT, 
	            		new HttpEntity<StationDTO>(new StationDTO(s)),
	            		StationDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
