package ftn.kts.transport.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.repositories.LineRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LineRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LineRepository lineRepository;
	
	@Test
	public void testAddNewLine() {
		Line newLine = new Line();
		newLine.setName("Linija");
		newLine.setTransportType(VehicleType.BUS);
		entityManager.persist(newLine);
		entityManager.flush();
		
		Line found = lineRepository.findByName(newLine.getName()).get();
		assertEquals(found.getName(), newLine.getName());
		
	}
}
