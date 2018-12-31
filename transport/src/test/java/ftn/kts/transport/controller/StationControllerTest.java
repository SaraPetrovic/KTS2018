package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class StationControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private StationService stationService;
	

	@Before
	public void setUp() {
		List<Station> stations = new ArrayList<Station>();
		Station station = new Station("Jevrejska 13", "Centar", true);
		stations.add(station);
		Mockito.when(stationService.findAll()).thenReturn(stations);
		
		StationDTO dtoStation = new StationDTO(Long.valueOf(1), "Jevrejska 13", "Centar");
		Mockito.when(stationService.save(station)).thenReturn(station);
	}
	
//	@Test
//	public void getAllTest() {
//		ResponseEntity<List<StationDTO>> responseEntity = 
//				restTemplate.exchange("/station/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<StationDTO>>() {
//				});
//		
//		List<StationDTO> rez = responseEntity.getBody();
//		
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
	
	@Test
	public void addStationTest() {
		

//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("X-TP-DeviceID", "your value");
//
//		HttpEntity<StationDTO> entity = new HttpEntity<StationDTO>( new StationDTO(Long.valueOf(1), "Jevrejska 13", "Centar"), headers);
//
//		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/add/", HttpMethod.POST, entity, StationDTO.class);

		
		
		ResponseEntity<StationDTO> responseEntity = 
				restTemplate.postForEntity("/station/add", new StationDTO(Long.valueOf(1), "Jevrejska 13", "Centar"), StationDTO.class);
	
		StationDTO rez = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	
	
}
