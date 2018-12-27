package ftn.kts.transport.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.repositories.StationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private StationRepository stationRepository;
	
	@Before
	public void setUp() {
			Station station = new Station("Jevrejska 13", "Centar", true);
			entityManager.persist(station);
			entityManager.flush();
	}
	
	@Test
	public void findByNameTestOK() {
		
		Station found = stationRepository.findByName("Centar");
		
		assertNotNull(found);
		assertEquals("Jevrejska 13", found.getAddress());
	}
	
	@Test
	public void findByNameTestNotOK() {
		
		Station found = stationRepository.findByName("Centar 2");
		
		assertNull(found);
	}
}
