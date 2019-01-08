package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
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

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.services.LineService;

@RunWith(SpringRunner.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(DTOConverter.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LineControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private LineService serviceMocked;
	
	private LineDTO lineDTO;
	private Line line;
	
	@Before
	public void setUp() {
		lineDTO = new LineDTO();
		lineDTO.setName("Mocked station");
		lineDTO.setVehicleType(0);
		
		line = new Line();
		line.setName("Mocked station");
		line.setTransportType(VehicleType.BUS);
		line.setId(1L);

		//PowerMockito.mockStatic(DTOConverter.class);
		Mockito.when(DTOConverter.convertDTOtoLine(lineDTO)).thenReturn(line);
		
		Mockito.when(serviceMocked.addLine(line)).thenReturn(line);
		Mockito.when(serviceMocked.addStationsToLine(1L, lineDTO)).thenReturn(line);
		
	}
	
	@Test
	public void addLineTest() {
		ResponseEntity<LineDTO> responseEntity = 
				restTemplate.postForEntity("/line", lineDTO, LineDTO.class);
		LineDTO ret = responseEntity.getBody();
		assertEquals("Mocked station", ret.getName());
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
}
