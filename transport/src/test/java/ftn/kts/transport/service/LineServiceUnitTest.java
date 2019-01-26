package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.RouteScheduleRepository;
import ftn.kts.transport.repositories.StationRepository;
import ftn.kts.transport.services.LineService;
import ftn.kts.transport.services.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class LineServiceUnitTest {

	@Autowired
	private LineService service;
	
	@SpyBean
	private LineService spyService;
	
	@MockBean
	private LineRepository lineRepoMocked;
	
	@MockBean
	private StationRepository stationRepoMocked;
	
	@MockBean
	private RouteScheduleRepository routeRepoMocked;
	
	@MockBean
	private ZoneService zoneServiceMocked;
	
	private Line l, l2;
	private RouteSchedule sch;
	private Station s;
	
	@Before
	public void setUp() {
		l = new Line();
		l.setId(1L);
		l.setName("1A");
		l.setTransportType(VehicleType.BUS);
		
		l2 = new Line();
		l2.setName("1A");
		l2.setTransportType(VehicleType.BUS);
		
		s = new Station();
		s.setId(1L);
		s.setName("Stanica 1");
		
		Mockito.when(lineRepoMocked.save(l)).thenReturn(l).thenThrow(new DAOException("Duplicate entry"));
		Mockito.when(lineRepoMocked.findById(1L)).thenReturn(Optional.of(l));
		Mockito.when(lineRepoMocked.findById(-1L)).thenThrow(new DAOException("Line not found"));
		Mockito.when(lineRepoMocked.findByName("1A")).thenReturn(Optional.of(l));
		Mockito.when(lineRepoMocked.findByName("A5")).thenThrow(new DAOException("Line not found"));
		
		sch = new RouteSchedule();
		sch.setActive(true);
		sch.setLine(l);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
		Set<Date> weekday = new HashSet<Date>();
		Set<Date> saturday = new HashSet<Date>();
		Set<Date> sunday = new HashSet<Date>();
		Date activeFrom, weekday1, weekday2, weekday3, saturday1, sunday1;
		try {
			activeFrom = formatter.parse("01.12.2018. 05:00");
			weekday1 = formatter2.parse("12:00");
			weekday2 = formatter2.parse("13:00");
			weekday3 = formatter2.parse("14:00");
			saturday1 = formatter2.parse("15:00");
			sunday1 = formatter2.parse("16:00");
			weekday.add(weekday3);
			weekday.add(weekday2);
			weekday.add(weekday1);
			saturday.add(saturday1);
			sunday.add(sunday1);
			sch.setactiveFrom(activeFrom);
			sch.setWeekday(weekday);
			sch.setSaturday(saturday);
			sch.setSunday(sunday);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Mockito.when(routeRepoMocked.findById(1L)).thenReturn(Optional.of(sch));
		Mockito.when(routeRepoMocked.findById(-1L)).thenThrow(new DAOException("Schedule not found"));
		Mockito.when(routeRepoMocked.save(sch)).thenReturn(sch).thenThrow(new DAOException("Duplicate entry"));
		
		
	}
	
	@Test
	public void findAll_PASS_Test() {
		List<Line> all = new ArrayList<Line>();
		all.add(l);
		Mockito.when(lineRepoMocked.findAll()).thenReturn(all);
		int length = service.getAllLines().size();
		
		assertEquals(all.size(), length);
	}
	
	@Test
	public void findByName_PASS_Test() {
		String actual = service.findByName("1A").getName();
		assertEquals(l.getName(), actual);
	}
	
	@Test(expected = DAOException.class)
	public void findByName_NotFound_Test() {
		service.findByName("A5");
	}
	
	
	@Test
	public void findLineById_PASS_Test() {
		Line ret = service.findById(1L);
		assertEquals(l.getName(), ret.getName());
	}
	
	@Test(expected = DAOException.class)
	public void findLineById_NotFound_Test() {
		// not found -> throw DAO
		service.findById(-1L);
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
	public void addLine_AlreadyExists_Test() {
		Line duplicate = new Line();
		duplicate.setName("1A");
		Mockito.when(lineRepoMocked.save(duplicate)).thenThrow(new DataIntegrityViolationException("Duplicate entry!"));
		
		service.addLine(duplicate);
	
	}
	
	@Test
	public void addStationsToLine_PASS_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, s.getId());
		ArrayList<Long> stationIds = new ArrayList<Long>();
		stationIds.add(s.getId());
		ArrayList<Station> foundStations = new ArrayList<Station>();
		foundStations.add(s);
		Mockito.when(stationRepoMocked.findByIdIn(stationIds)).thenReturn(foundStations);
		
		LineDTO dto = new LineDTO();
		dto.setStations(stations);
		
		Line ret = service.addStationsToLine(1L, dto);
		int stationsCount = ret.getStationSet().size();
		assertEquals(stations.size(), stationsCount);
	}
	
	@Test(expected = DAOException.class)
	public void addStationsToLine_SomeStationNotFound_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, s.getId());
		ArrayList<Long> stationIds = new ArrayList<Long>();
		stationIds.add(s.getId());
		ArrayList<Station> foundStations = new ArrayList<Station>();
		//foundStations.add(s);		-- bude prazna lista pa throwuje DAO
		Mockito.when(stationRepoMocked.findByIdIn(stationIds)).thenReturn(foundStations);
		
		LineDTO dto = new LineDTO();
		dto.setStations(stations);
		
		service.addStationsToLine(1L, dto);
		
	}
	
	@Test(expected = DAOException.class)
	public void addStationsToLine_LineNotFound_Test() {
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, s.getId());
		ArrayList<Long> stationIds = new ArrayList<Long>();
		stationIds.add(s.getId());
		ArrayList<Station> foundStations = new ArrayList<Station>();
		foundStations.add(s);		
		Mockito.when(stationRepoMocked.findByIdIn(stationIds)).thenReturn(foundStations);
		
		LineDTO dto = new LineDTO();
		dto.setStations(stations);
		
		service.addStationsToLine(-1L, dto);
	}
	
	
	@Test
	public void updateLine_PASS_Test() {
		LineDTO dto = new LineDTO();
		dto.setName("Novo ime");
		dto.setVehicleType(1);
		// updated
		Line ret = service.updateLine(dto, 1);
		assertEquals(dto.getName(), ret.getName());
		assertEquals(VehicleType.values()[dto.getVehicleType()], ret.getTransportType());
		
	}
	
	@Test(expected = DAOException.class)
	public void updateLine_NotFound_Test() {
		LineDTO dto = new LineDTO();
		dto.setName("Novo ime");
		dto.setVehicleType(1);
		// id=-1  ->  not found
		service.updateLine(dto, -1);
	}
	
	@Test
	public void deleteLine_PASS_Test() {
		long id = 1;
		// deleted
		Line ret = service.deleteLine(id);
		assertFalse(ret.isActive());
	}
	
	@Test(expected = DAOException.class)
	public void deleteLine_NotFound_Test() {
		// not found
		service.deleteLine(-1);
	}
	
	@Test
	public void updateLineStations_PASS_Test() {
		Mockito.doReturn(this.l).when(spyService).findById(1L);
		Mockito.when(lineRepoMocked.save(this.l)).thenReturn(this.l);
		
		LineDTO dto = new LineDTO();
		HashMap<Integer, Long> stations = new HashMap<Integer, Long>();
		stations.put(1, 1L);
		dto.setStations(stations);
		
		HashSet<LineAndStation> stationSet = new HashSet<LineAndStation>();
		LineAndStation ls = new LineAndStation();
		ls.addStation(this.l, this.s, 1);
		stationSet.add(ls);
		this.l.setStationSet(stationSet);
		Mockito.doReturn(this.l).when(spyService).addStationsToLine(1L, dto);
		
		Line ret = spyService.updateLineStations(1L, dto);
		assertNotNull(ret);
		assertEquals(this.l.getStationSet().size(), ret.getStationSet().size());
	}
	
	
	
	@Test
	public void addLineMethod_PASS_Test() {
		LineAndStation ls = new LineAndStation();
		ls.addStation(l2, s, 1);
		HashSet<LineAndStation> stations = new HashSet<LineAndStation>();
		stations.add(ls);
		l2.setStationSet(stations);
		
		HashMap<Integer, Long> dtoStations = new HashMap<Integer, Long>();
		dtoStations.put(1, 1L);
		LineDTO lineDTO = new LineDTO();
		lineDTO.setStations(dtoStations);
		
		
		Mockito.doReturn(l).when(spyService).addLine(l);
		Mockito.doReturn(l2).when(spyService).addStationsToLine(l.getId(), lineDTO);
		
		Line ret = service.addLineMethod(l, lineDTO);
		
		assertNotNull(ret.getStationSet());
		assertEquals(l.getName(), ret.getName());
		
		Mockito.verify(spyService, Mockito.times(1)).addLine(l);
		Mockito.verify(spyService, Mockito.times(1)).addStationsToLine(l.getId(), lineDTO);
		
		assertEquals(stations.size(), ret.getStationSet().size());
		
	}
	
}
