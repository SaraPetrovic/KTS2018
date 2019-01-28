package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Point;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.repositories.StationRepository;
import ftn.kts.transport.services.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class StationServiceUnitTest {

	@Autowired
	private StationService stationService;
	@MockBean
	private StationRepository stationRepository;
	
	@Test
	public void fromDtoToStationTestOK1() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", true);
		StationDTO dtoStation = new StationDTO(Long.valueOf(1), "Jevrejska 13", "Centar", new Point(10,10));
		Mockito.when(stationRepository.findById(dtoStation.getId())).thenReturn(Optional.of(station));
	
		Station rez = stationService.fromDtoToStation(dtoStation);
		
		assertNotNull(rez);
		assertEquals(station.getAddress(), rez.getAddress());
	}
	
	@Test
	public void fromDtoToStationTestOK2() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", true);
		StationDTO dtoStation = new StationDTO("Jevrejska 13", "Centar");
		Mockito.when(stationRepository.findByName(dtoStation.getName())).thenReturn(station);
		
		Station rez = stationService.fromDtoToStation(dtoStation);
		
		assertNotNull(rez);
		assertEquals(station.getAddress(), rez.getAddress());
	}
	
	@Test
	public void fromDtoToStationTestReturnNull() {
		StationDTO dtoStation = new StationDTO();

		Station station = stationService.fromDtoToStation(dtoStation);
		
		assertNull(station);
	}
	
	@Test
	public void saveTest() {
		Station station = new Station("Jevrejska 13", "Centar", true);
		Mockito.when(stationRepository.save(station)).thenReturn(station);
		
		Station saveObject = stationService.save(station);
	
		assertNotNull(saveObject);
		assertEquals(station, saveObject);
	}
	
	@Test
	public void findByIdTestOK() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", true);
		Mockito.when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));
		
		Station rez = stationService.findById(station.getId());
		
		assertNotNull(rez);
		assertEquals(station, rez);
	}
	
	@Test(expected=StationNotFoundException.class)
	public void findByIdTestNotOK1() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", false);
		Mockito.when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));
		
		stationService.findById(station.getId());
	}
	
	@Test(expected=StationNotFoundException.class)
	public void findByIdTestNotOK2() {
		Mockito.when(stationRepository.findById(Long.valueOf(2))).thenThrow(StationNotFoundException.class);
		
		stationService.findById(Long.valueOf(2));
	}
	
	@Test
	public void findByNameTestOK() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", true);
		Mockito.when(stationRepository.findByName(station.getName())).thenReturn(station);
		
		Station rez = stationService.findByName(station.getName());
		
		assertNotNull(rez);
		assertEquals(station.getId(), station.getId());
	}
	
	@Test(expected=StationNotFoundException.class)
	public void findByNameTestNotOK1() {
		Station station = new Station(Long.valueOf(1), "Jevrejska 13", "Centar", false);
		Mockito.when(stationRepository.findByName(station.getName())).thenReturn(station);
		
		Station rez = stationService.findByName(station.getName());
		
		assertNotNull(rez);
		assertEquals(station.getId(), station.getId());
	}
	
	@Test(expected=StationNotFoundException.class)
	public void findByNameTestNotOK2() {
		Mockito.when(stationRepository.findByName("Centar")).thenReturn(null);
		
		Station rez = stationService.findByName("Centar");
		
		assertNull(rez);
	}
	
	@Test
	public void findAllTest() {
		Station station = new Station(Long.valueOf(1), "Futoska 13", "Centar", true);
		Station station1 = new Station(Long.valueOf(2), "Jevrejska 13", "Centar", true);
		Station station2 = new Station(Long.valueOf(3), "Jevrejska 53", "Centar", false);
		Station station3 = new Station(Long.valueOf(4), "Bulevar Mihajla Pupina 1", "Centar", true);
		
		List<Station> stations = new ArrayList<Station>();
		stations.add(station);
		stations.add(station1);
		stations.add(station2);
		stations.add(station3);
		
		Mockito.when(stationRepository.findAll()).thenReturn(stations);
		
		List<Station> rez = stationService.findAll();
		
		assertNotEquals(stations, rez);
		assertEquals(stations.size() -1, rez.size());
	}
	
	@Test
	public void deleteTestReturnTrue() {
		Station s = new Station(Long.valueOf(1), "Futoska 13", "Centar", new HashSet<LineAndStation>(), true);
		Mockito.when(stationRepository.findById(s.getId())).thenReturn(Optional.of(s));
		
		boolean rez = stationService.delete(s.getId());
		
		assertTrue(rez);
	}
	
	@Test(expected=StationNotFoundException.class)
	public void deleteTestNotOK() {
		Mockito.when(stationRepository.findById(Long.valueOf(1))).thenThrow(StationNotFoundException.class);
		
		stationService.delete(Long.valueOf(1));
	}
	
	
	
}
