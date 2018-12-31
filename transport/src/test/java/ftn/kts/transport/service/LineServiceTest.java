package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

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
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.RouteScheduleRepository;
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
	
	@MockBean
	private RouteScheduleRepository routeRepoMocked;
	
	private Line l;
	private RouteSchedule sch;
	
	@Before
	public void setUp() {
		l = new Line();
		l.setName("1A");
		l.setTransportType(VehicleType.BUS);
		
		Mockito.when(lineRepoMocked.save(l)).thenReturn(l).thenThrow(new DAOException("Duplicate entry"));
		Mockito.when(lineRepoMocked.findById(1L)).thenReturn(Optional.of(l));
		Mockito.when(lineRepoMocked.findById(-1L)).thenThrow(new DAOException("Line not found"));
		
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
	
	@Test(expected = DAOException.class)
	public void findLineByIdTest() {
		Line ret = service.findById(1L);
		assertEquals(l.getName(), ret.getName());
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
	
	@Test(expected = DAOException.class)
	public void findScheduleByIdTest() {
		RouteSchedule ret = service.findScheduleById(1L);
		assertNotNull(ret);
		service.findScheduleById(-1L);
	}
	
	@Test(expected = DAOException.class)
	public void addScheduleTest() {
		RouteSchedule ret = service.addSchedule(sch, 1L);
		assertNotNull(ret);
		assertEquals(sch.getactiveFrom(), ret.getactiveFrom());
		service.addSchedule(sch, -1L);
	}
	
	@Transactional
	@Test(expected = DAOException.class)
	public void updateScheduleTest() {
		Date d = new Date();
		sch.setactiveFrom(d);
		RouteSchedule ret = service.updateSchedule(sch, 1L, 1L);
		assertEquals(d, ret.getactiveFrom());
		
		// schedule [id=-1] doesn't exist!
		ret = service.updateSchedule(sch, 1L, -1L);
	}
	
	@Test
	public void deleteScheduleTest() {
		boolean ret = service.deleteSchedule(1L);
		assertTrue(ret);
		RouteSchedule s = service.findScheduleById(1L);
		assertFalse(s.isActive());
	}
	
}
