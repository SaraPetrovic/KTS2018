package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.exception.ZoneNotFoundException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ZoneServiceUnitTest {

	@Autowired
	private ZoneService zoneService;
	
	@MockBean
	private ZoneRepository zoneRepository;
	
	@SpyBean
	private ZoneService spyZoneService;
	
	@Before
	public void setUp() {
		Mockito.when(zoneRepository.findById(Long.valueOf(5))).thenThrow(ZoneNotFoundException.class);
	
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		Zone zone1 = new Zone(Long.valueOf(10), "Gradska", true);
		Mockito.when(zoneRepository.findById(zone1.getId())).thenReturn(Optional.of(zone));
		
		Set<Station> stationsEmpty = new HashSet<Station>();
		Zone zone2 = new Zone(Long.valueOf(2), "Gradska", stationsEmpty, zone, true);
		Mockito.when(zoneRepository.findById(zone2.getId())).thenReturn(Optional.of(zone2));
		
		Set<Station> stations = new HashSet<Station>();
		stations.add(new Station("Jevrejska 13", "Centar", true));
		Zone zone11 = new Zone(Long.valueOf(11), "Gradska", stations, null, true);
		Mockito.when(zoneRepository.findById(zone11.getId())).thenReturn(Optional.of(zone11));
		
	}
	
	@Test
	public void saveTest() {
		Zone zone = new Zone(Long.valueOf(18), "Gradska", null, null, true);
		Mockito.when(zoneRepository.save(zone)).thenReturn(zone);
		
		Zone rez = zoneService.save(zone);
		assertNotNull(rez);
		assertEquals(zone, rez);
	}
	
	@Test
	public void addZoneTestOK1() {
		Zone zone1 = new Zone(Long.valueOf(1), "Gradska", null, null, true);
		Zone zone2 = new Zone(Long.valueOf(2), "Prigradska", null, zone1, true);
		Mockito.when(zoneRepository.findByName(zone2.getName())).thenReturn(null);
		Mockito.when(zoneRepository.save(zone2)).thenReturn(zone2);
		
		Zone rez = zoneService.save(zone2);
		
		assertNotNull(rez);
		assertEquals(zone2, rez);
	}
	
	@Test
	public void addZoneTestOK2() {
		Zone zone1 = new Zone(Long.valueOf(1), "Gradska", null, null, true);
		Zone zone2 = new Zone(Long.valueOf(2), "Prigradska", null, zone1, true);
		Zone zone3 = new Zone(Long.valueOf(3), "Prigradska 2", null, zone2, true);
		Zone zone4 = new Zone(Long.valueOf(4), "Prigradska 3", null, zone2, true);

		Mockito.when(zoneRepository.findByName(zone4.getName())).thenReturn(null);
		Mockito.when(zoneRepository.save(zone4)).thenReturn(zone4);
		
		Zone rez = zoneService.addZone(zone4);
		
		assertNotNull(rez);
		assertEquals(zone4, rez);
	}
	
	@Test(expected=InvalidInputDataException.class)
	public void addZoneTestConflict() {
		Zone zone1 = new Zone(Long.valueOf(1), "Gradska", null, null, true);
		Zone zone2 = new Zone(Long.valueOf(2), "Prigradska", null, zone1, true);
		Mockito.when(zoneRepository.findByName(zone2.getName())).thenReturn(zone2);

		zoneService.addZone(zone2);
	}
	
	@Test
	public void deleteZoneTestOK1() {
		boolean rez = zoneService.deleteZone(Long.valueOf(2));
		
		assertTrue(rez);
	}
	
	@Test
	public void deleteZoneTestOK2() {
		Set<Station> stations = new HashSet<Station>();
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
		
		assertEquals(zone.getId(), zone3.getSubZone().getId());
		assertTrue(rez);
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void deleteZoneTestZoneNotFound() {
		zoneService.deleteZone(Long.valueOf(5));
	}
	
	@Test(expected=DAOException.class)
	public void deleteZoneTestBadRequest() {
		zoneService.deleteZone(Long.valueOf(11));
	}
	
	@Test
	public void findByIdTestOK() {
		
		Zone rez = zoneService.findById(Long.valueOf(10));
		
		assertNotNull(rez);
		assertEquals("Gradska", rez.getName());
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void findByIdTestNotOK1() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", false);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		zoneService.findById(zone.getId());
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void findByIdTestNotOK2() {
		zoneService.findById(Long.valueOf(5));
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
	
	@Transactional
	@Test
	public void getZoneForLine_PASS_Test() {
		Line l = new Line();
		l.setId(1L);
		l.setName("1A");
		l.setTransportType(VehicleType.BUS);
		Station s = new Station();
		s.setId(1L);
		s.setName("Stanica 1");
		Zone z1 = new Zone();
		Zone z2 = new Zone();
		z1.setActive(true);
		z1.setId(1L);
		z1.setName("Zone I");
		z1.setSubZone(null);
		z2.setActive(true);
		z2.setId(2L);
		z2.setName("Zone II");
		z2.setSubZone(z1);
		Set<Zone> foundZones = new HashSet<Zone>();
		foundZones.add(z1);
		foundZones.add(z2);
		
		//Mockito.when(zoneServiceMocked.getZonesByStations(Mockito.anyCollection())).thenReturn(foundZones);
		Mockito.doReturn(foundZones).when(spyZoneService).getZonesByStations(Mockito.anyCollection());
		
		LineAndStation ls = new LineAndStation();
		ls.addStation(l, s, 1);
		Set<LineAndStation> stationSet = new HashSet<LineAndStation>();
		stationSet.add(ls);
		l.setStationSet(stationSet);
		
		// jel ok spy?? 
		Zone retParent = spyZoneService.getZoneForLine(l);
		assertNotNull(retParent);
		assertSame(z2, retParent);
		assertEquals(z2.getId(), retParent.getId());
		assertEquals(z2.getSubZone().getId(), retParent.getSubZone().getId());
	}

	
}
