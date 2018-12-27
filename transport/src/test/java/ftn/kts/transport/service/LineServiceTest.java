package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

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
	
	private Line l;
	
	@Before
	public void setUp() {
		l = new Line();
		l.setName("1A");
		l.setTransportType(VehicleType.BUS);
		
		Mockito.when(lineRepoMocked.save(l)).thenReturn(l).thenThrow(new DAOException("Duplicate entry"));
		Mockito.when(lineRepoMocked.findById(1L)).thenReturn(Optional.of(l));
		Mockito.when(lineRepoMocked.findById(-1L)).thenThrow(new DAOException("Line not found"));
	}
	
	@Test(expected = DAOException.class)
	public void addLineTest() {
		// prvi put uspesno dodavanje -> return Line
		// drugi put baca exception za duplicate entry
		Line ret = service.addLine(l);
		assertEquals(l.getName(), ret.getName());
		service.addLine(l);
	}
	
	@Test(expected = DAOException.class)
	public void updateLineTest() {
		LineDTO dto = new LineDTO();
		dto.setName("Novo ime");
		dto.setVehicleType(1);
		// updated
		Line ret = service.updateLine(dto, 1);
		assertEquals(dto.getName(), ret.getName());
		assertEquals(VehicleType.values()[dto.getVehicleType()], ret.getTransportType());
		// id=-1  ->  not found
		service.updateLine(dto, -1);
		
	}
	
	@Test(expected = DAOException.class)
	public void deleteLineTest() {
		long id = 1;
		// deleted
		Line ret = service.deleteLine(id);
		assertFalse(ret.isActive());
		// not found
		service.deleteLine(-1);
	}
	
}
