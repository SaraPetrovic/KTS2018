package ftn.kts.transport.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.services.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LineControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private LineService serviceMocked;
	
	private LineDTO l;
	
	@Before
	public void setUp() {
		l = new LineDTO();
		l.setName("Mocked station");
		l.setVehicleType(0);
		
		//Mockito.when(serviceMocked.addLine(any(Line.class))).thenReturn(l);
		//Mockito.when(serviceMocked.add)
	}
	
	@Test
	public void addLineTest() {
		ResponseEntity<LineDTO> responseEntity = 
				restTemplate.postForEntity("/line", l, LineDTO.class);
		LineDTO ret = responseEntity.getBody();
		assertEquals("Mocked station", ret.getName());
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
}
