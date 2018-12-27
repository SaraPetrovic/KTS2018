package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.exception.ZoneNotFoundException;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ZoneServiceTest {

	@Autowired
	private ZoneService zoneService;
	
	@MockBean
	private ZoneRepository zoneRepository;
	
	@Test
	public void saveTest() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.save(zone)).thenReturn(zone);
		
		Zone rez = zoneService.save(zone);
		
		assertNotNull(rez);
		assertEquals(zone, rez);
	}
	
	@Test
	public void deleteZoneTestOK() {
		Zone zone = new Zone(Long.valueOf(1), "Gradska", true);
		Mockito.when(zoneRepository.findById(zone.getId())).thenReturn(Optional.of(zone));
		
		boolean rez = zoneService.deleteZone(zone.getId());
		
		assertTrue(rez);
	}
	
	@Test(expected=ZoneNotFoundException.class)
	public void deleteZoneTestNotOK() {
		Mockito.when(zoneRepository.findById(Long.valueOf(1))).thenThrow(ZoneNotFoundException.class);
		
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
