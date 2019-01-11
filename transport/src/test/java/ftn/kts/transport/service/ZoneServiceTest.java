package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.ZoneNotFoundException;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ZoneServiceTest {

	@Autowired
	private ZoneService zoneService;
	
	@MockBean
	private ZoneRepository zoneRepository;
	
	@Before
	public void setUp() {
		Mockito.when(zoneRepository.findById(Long.valueOf(5))).thenThrow(ZoneNotFoundException.class);
	
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		Zone zone2 = new Zone(Long.valueOf(2), "Gradska", null, zone, true);
		Mockito.when(zoneRepository.findById(zone2.getId())).thenReturn(Optional.of(zone2));
		
	}
	
	@Test
	public void saveTest() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.save(zone)).thenReturn(zone);
		
		Zone rez = zoneService.save(zone);
		
		assertNotNull(rez);
		assertEquals(zone, rez);
	}
	
	@Test
	public void deleteZoneTestOK1() {
		boolean rez = zoneService.deleteZone(Long.valueOf(2));
		
		assertTrue(rez);
	}
	
	@Test
	public void deleteZoneTestOK2() {
		Set<Station> stations = new HashSet<Station>();
		stations.add(new Station("Jevrejska 13", "Centar", true));
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Zone zone2 = new Zone(Long.valueOf(2), "Gradska2", stations, zone, true);
		Zone zone3 = new Zone(Long.valueOf(3), "Prigradska", null, zone2, true);
		List<Zone> zones = new ArrayList<Zone>();
		zones.add(zone);
		zones.add(zone2);
		zones.add(zone3);
		Mockito.when(zoneRepository.findById(zone2.getId())).thenReturn(Optional.of(zone2));
		Mockito.when(zoneRepository.findAll()).thenReturn(zones);
		
		boolean rez = zoneService.deleteZone(zone2.getId());
		
		assertEquals(stations.size(), zone.getStations().size());
		assertEquals(zone.getId(), zone3.getSubZone().getId());
		System.out.println(zone.getId() + " " + zone3.getSubZone().getId());
		assertTrue(rez);
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void deleteZoneTestNotOK1() {
		zoneService.deleteZone(Long.valueOf(5));
	}
	
	@Test(expected=DAOException.class)
	public void deleteZoneTestNotOK2() {
		zoneService.deleteZone(Long.valueOf(1));
	}
	
	@Test
	public void findByIdTestOK() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		Zone rez = zoneService.findById(zone.getId());
		
		assertNotNull(rez);
		assertEquals(zone, rez);
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void findByIdTestNotOK1() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", false);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		zoneService.findById(zone.getId());
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void findByIdTestNotOK2() {
		Mockito.when(zoneRepository.findById(Long.valueOf(2))).thenThrow(ZoneNotFoundException.class);
		
		zoneService.findById(Long.valueOf(2));
	}
	
	@Test
	public void findAllTest1() {
		Zone zone1 = new Zone(Long.valueOf(1), "Gradska", true);
		Zone zone2 = new Zone(Long.valueOf(2), "Prigradska", true);
		Zone zone3 = new Zone(Long.valueOf(3), "Medjugradska", true);
		
		List<Zone> zones = new ArrayList<Zone>();
		zones.add(zone3);
		zones.add(zone2);
		zones.add(zone1);
		
		Mockito.when(zoneRepository.findAll()).thenReturn(zones);
		
		List<Zone> rez = zoneService.findAll();
		
		assertEquals(zones.size(), rez.size());
		assertEquals(zones, rez);
	}
	
	@Test
	public void findAllTest2() {
		Zone zone1 = new Zone(Long.valueOf(1), "Gradska", true);
		Zone zone2 = new Zone(Long.valueOf(2), "Prigradska", false);
		Zone zone3 = new Zone(Long.valueOf(3), "Medjugradska", false);
		
		List<Zone> zones = new ArrayList<Zone>();
		zones.add(zone3);
		zones.add(zone2);
		zones.add(zone1);
		
		Mockito.when(zoneRepository.findAll()).thenReturn(zones);
		
		List<Zone> rez = zoneService.findAll();
		
		assertEquals(zones.size() - 2, rez.size());
		assertNotEquals(zones, rez);
	}
	
}
