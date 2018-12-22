package ftn.kts.transport.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.StationRepository;
import ftn.kts.transport.services.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LineServiceTest {

	@Autowired
	private LineService service;
	
	@MockBean
	private LineRepository lineRepoMocked;
	
	@MockBean
	private StationRepository stationRepoMocked;
	
	@Before
	public void setUp() {
		Line l1 = new Line();
		l1.setName("1A");
		l1.setTransportType(VehicleType.BUS);
		
		Line l2Duplicate = new Line();
		l2Duplicate.setName("1A");
		l2Duplicate.setTransportType(VehicleType.BUS);
		
		//Mockito.when(lineRepoMocked.save(any(Line.class)).thenReturn(l1).thenThrow(new DAOException("Duplicate entry for line [" + l2Duplicate.getName() + "]"));
		//Mockito.when(lineRepoMocked.save(l2Duplicate)).thenThrow(new DAOException("Duplicate entry for line [" + l2Duplicate.getName() + "]"));
		
	}
	
	@Test
	public void someTest() {
		
		LineDTO lineDTO = new LineDTO();
		lineDTO.setName("1A");
		Line ret = service.addLine(lineDTO);
		assertEquals(lineDTO.getName(), ret.getName());
		//assertEquals(1,1);
	}
}
