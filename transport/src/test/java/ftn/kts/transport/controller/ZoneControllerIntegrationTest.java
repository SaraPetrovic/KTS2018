package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.exception.ZoneNotFoundException;
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
	public void getZoneTestOK() {
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.getForEntity("/zone/" + Long.valueOf(2), ZoneDTO.class);
	
		ZoneDTO rez = responseEntity.getBody();
		
		assertNotNull(rez);
	}
	
	@Test
	public void getZoneTestNotFound() {
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.getForEntity("/zone/" + Long.valueOf(52), ZoneDTO.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void getZoneTestNotFound2() {
		ResponseEntity<ZoneDTO> responseEntity = 
				restTemplate.getForEntity("/zone/" + Long.valueOf(7), ZoneDTO.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void getAllTest() {
		ResponseEntity<ZoneDTO[]> responseEntity = 
				restTemplate.getForEntity("/zone", ZoneDTO[].class);
		
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
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<ZoneDTO> entity = new HttpEntity<ZoneDTO>(new ZoneDTO("Zona44", null, Long.valueOf(5)), headers);

		ResponseEntity<ZoneDTO> responseEntity =
				restTemplate.exchange("/rest/zone", HttpMethod.POST,
						entity, ZoneDTO.class);
		
	
		ZoneDTO rez = responseEntity.getBody();

		assertNotNull(rez);
		assertEquals(size + 1, zoneService.findAll().size());
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	public void addZoneTestOK2() {
		int size =zoneService.findAll().size();
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<ZoneDTO> entity = new HttpEntity<ZoneDTO>(new ZoneDTO("Zona55", null, null), headers);

		ResponseEntity<ZoneDTO> responseEntity =
				restTemplate.exchange("/rest/zone", HttpMethod.POST,
						entity, ZoneDTO.class);
		
		
		ZoneDTO rez = responseEntity.getBody();

		assertNotNull(rez);
		assertEquals(size + 1, zoneService.findAll().size());
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	
	}

	@Test
	public void addZoneTestZoneNotFoundSubZone() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<ZoneDTO> entity = new HttpEntity<ZoneDTO>(new ZoneDTO("Zona55", null, Long.valueOf(88)), headers);

		ResponseEntity<ZoneDTO> responseEntity =
				restTemplate.exchange("/rest/zone", HttpMethod.POST,
						entity, ZoneDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void addZoneTestZoneConflict() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<ZoneDTO> entity = new HttpEntity<ZoneDTO>( new ZoneDTO("Zona II", null, Long.valueOf(1)), headers);

		ResponseEntity<ZoneDTO> responseEntity =
				restTemplate.exchange("/rest/zone", HttpMethod.POST,
						entity, ZoneDTO.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
	
	@Test
	public void addZoneTestBadRequest() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<ZoneDTO> entity = new HttpEntity<ZoneDTO>(new ZoneDTO("", null, Long.valueOf(2)), headers);

		ResponseEntity<ZoneDTO> responseEntity =
				restTemplate.exchange("/rest/zone", HttpMethod.POST,
						entity, ZoneDTO.class);
		
		responseEntity.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteZoneTestOK() {
		
		int size = zoneService.findAll().size();
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/zone/" + Long.valueOf(4),
						HttpMethod.DELETE, new HttpEntity<Object>(null, headers), Void.class);
	
		Zone z5 = zoneService.findById(Long.valueOf(5));
		
		assertEquals(Long.valueOf(3), z5.getSubZone().getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, zoneService.findAll().size());
	}
	
	@Test
	public void deleteZoneTestBadRequest() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Set<Station> stations = new HashSet<Station>();
		stations.add(stationService.findById(Long.valueOf(5)));
		
		Zone zone = zoneService.save(new Zone(Long.valueOf(45), "Naziv", stations, null, true));
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.exchange("/rest/zone/" + zone.getId(),
						HttpMethod.DELETE, new HttpEntity<Void>(null, headers), Void.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	

	@Test
	public void updateZoneTestBadRequest() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Zone zone = zoneService.findById(Long.valueOf(2));
		zone.setName(null);
		
		ZoneDTO dto = new ZoneDTO(zone.getId(), zone.getName(), null, null);

		ResponseEntity<ZoneDTO> responseEntity =
	            restTemplate.exchange("/rest/zone/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<ZoneDTO>(dto, headers),
	            		ZoneDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateZoneTestNotFound() {

		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		ZoneDTO dto = new ZoneDTO(Long.valueOf(99), null, null, null);
		
		ResponseEntity<ZoneDTO> responseEntity =
	            restTemplate.exchange("/rest/zone/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<ZoneDTO>(dto, headers),
	            		ZoneDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateZoneTestOK() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Zone zone = zoneService.findById(Long.valueOf(2));
		
		ZoneDTO dto = new ZoneDTO(zone.getId(), "Druga zona", null, null);
		
		ResponseEntity<ZoneDTO> responseEntity =
	            restTemplate.exchange("/rest/zone/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<ZoneDTO>(dto, headers),
	            		ZoneDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Druga zona", responseEntity.getBody().getName());
	}

	@Test
	public void updateZoneTestCONFLICT() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		
		Zone zone = zoneService.findById(Long.valueOf(2));
		
		ZoneDTO dto = new ZoneDTO(zone.getId(), zone.getName(), null, null);
		
		ResponseEntity<ZoneDTO> responseEntity =
	            restTemplate.exchange("/rest/zone/" + dto.getId(), HttpMethod.PUT, 
	            		new HttpEntity<ZoneDTO>(dto, headers),
	            		ZoneDTO.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
}
