package ftn.kts.transport.controller;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.DTOconverter.DTOConverter;
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
	@MockBean
	private DTOConverter dtoConverterMocked;
	
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
		Mockito.when(dtoConverterMocked.convertDTOtoLine(lineDTO)).thenReturn(line);
		
		Mockito.when(serviceMocked.addLine(line)).thenReturn(line);
		Mockito.when(serviceMocked.addStationsToLine(1L, lineDTO)).thenReturn(line);
		
		
	}
	
	@Test
	public void getLinesTest() {
		List<Line> l = new ArrayList<Line>();
		l.add(line);
		Mockito.when(serviceMocked.getAllLines()).thenReturn(l);
		
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/line", List.class);
		List<Line> lines = responseEntity.getBody();
		assertFalse(lines.isEmpty());
	}
	
	@Test
	public void addLineTest() {
		

		
		/*ResponseEntity<Line> responseEntity = 
				restTemplate.postForEntity("/line", lineDTO, Line.class);
		

		
		Line ret = responseEntity.getBody();
		//assertEquals("Mocked station", ret.getName());
		assertNotNull(ret);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		*/
	}
}
