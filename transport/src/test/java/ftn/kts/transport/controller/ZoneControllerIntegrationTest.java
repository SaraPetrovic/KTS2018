package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.services.StationService;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
//@Transactional
@Rollback
public class ZoneControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private StationService stationService;
	
	@Test
	public void getAllTest() {
		ResponseEntity<ZoneDTO[]> responseEntity = 
				restTemplate.getForEntity("/zone/all", ZoneDTO[].class);
		
		ZoneDTO[] rez = responseEntity.getBody();
		
		for(ZoneDTO z : rez) {
			System.out.println(z.getId() + " - " + z.getSubZoneId());
		}
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(rez);
	}
	
	@Test
	public void addZoneTestOK() {
		int size =zoneService.findAll().size();
		
		ZoneDTO entity= new ZoneDTO("Zona44", null, Long.valueOf(5));
		
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/add", entity, ZoneDTO.class);
	
		ZoneDTO rez = responseEntity.getBody();

		Zone z = zoneService.findById(Long.valueOf(6));
		assertEquals(rez.getId(), z.getSubZone().getId());
		assertEquals(size + 1, zoneService.findAll().size());
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	public void addZoneTestZoneNull() {
		
		ZoneDTO entity= new ZoneDTO(Long.valueOf(55), "Zona55", null, null);
		
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/add", entity, ZoneDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	
	@Test
	public void addZoneTestZoneNotFound() {
		
		ZoneDTO entity= new ZoneDTO(Long.valueOf(55), "Zona55", null, Long.valueOf(88));
		
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/add", entity, ZoneDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteZoneTestOK() {
		
		int size = zoneService.findAll().size();
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/zone/delete/" + Long.valueOf(4),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		Zone z5 = zoneService.findById(Long.valueOf(5));
		
		assertEquals(Long.valueOf(3), z5.getSubZone().getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, zoneService.findAll().size());
	}
	
	@Test
	public void deleteZoneTestBadRequest() {
		
		Set<Station> stations = new HashSet<Station>();
		stations.add(stationService.findById(Long.valueOf(5)));
		
		Zone zone = zoneService.save(new Zone(Long.valueOf(45), "Naziv", stations, null, true));
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/zone/delete/" + zone.getId(),
						HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/*
	@Test
	public void addStationsInZoneTestNotOK() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/zone/addStations/" + Long.valueOf(66), null, Void.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void addStationsInZoneTestNotOK1() {
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/zone/addStations/" + Long.valueOf(2), null, Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addStationsInZoneTestOK() {
		
		List<StationDTO> stations = new ArrayList<StationDTO>();
		Station station = stationService.findById(Long.valueOf(3));
		StationDTO dto = new StationDTO(station.getId(), station.getAddress(), station.getName());
		stations.add(dto);
		
		ResponseEntity<Void> responseEntity =
				restTemplate.postForEntity("/zone/addStations/" + Long.valueOf(2), stations, Void.class);
		
		assertEquals(2, zoneService.findById(Long.valueOf(2)).getStations().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	*/
	
	@Test
	public void updateZoneTestBadRequest() {
		
		Zone zone = zoneService.findById(Long.valueOf(2));
		zone.setName("");
		
		ZoneDTO dto = new ZoneDTO(zone.getId(), zone.getName(), null, null);
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/update", dto, ZoneDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateZoneTestNotFound() {
		
		ZoneDTO dto = new ZoneDTO(Long.valueOf(99), null, null, null);
		
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/update", dto, ZoneDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Ignore
	@Test
	public void updateZoneTestOK() {
		
		Zone zone = zoneService.findById(Long.valueOf(2));
		
		ZoneDTO dto = new ZoneDTO(zone.getId(), zone.getName(), null, null);
		
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.postForEntity("/zone/update", dto, ZoneDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
