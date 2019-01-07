package ftn.kts.transport.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.RouteScheduleRepository;
import ftn.kts.transport.repositories.StationRepository;

@Service
public class LineServiceImpl implements LineService {

	@Autowired
	private LineRepository lineRepository;
	@Autowired
	private StationRepository stationRepository;
	@Autowired
	private RouteScheduleRepository scheduleRepository;
	@Autowired
	private ZoneService zoneService;
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	
	@Override
	public List<Line> getAllLines(){
		return this.lineRepository.findAll();
	}


	@Override
	public Line findById(Long id) {
		Line line = lineRepository.findById(id).orElseThrow(() -> 
									new DAOException("Line [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));
		return line;
	}
	
	@Override
	public Line addLine(Line line) throws DAOException {
		
		try {
			return lineRepository.save(line);	
		} catch(DataIntegrityViolationException e) {
			throw new DAOException("Duplicate entry for line [" + line.getName() + "]", HttpStatus.CONFLICT);
		} 
	}
	
	
	
	@Override
	public Line updateLine(LineDTO line, long id) throws DAOException {
		Line found = lineRepository.findById(id).orElseThrow(() -> 
						new DAOException("Line [id=" + id + "] cannot be updated because it cannot be found.", HttpStatus.CONFLICT));
		
		found.setName(line.getName());
		found.setTransportType(VehicleType.values()[line.getVehicleType()]);
		lineRepository.save(found);
		return found;
		
	}
	
	@Override
	public Line deleteLine(long id) throws DAOException {
		// proveri da li sme da se brise (zbog ruta itd..)
		Line found = lineRepository.findById(id).orElseThrow(() ->
						new DAOException("Line [id=" + id + "] cannot be deleted because it cannot be found.", HttpStatus.CONFLICT));
		
		found.setActive(false);
		lineRepository.save(found);
		return found;
	}


	// u lineDTO prosledjujem mapu (order, stationID)
	// i onda iscupam sve stanice iz repo

	@Override
	public Line addStationsToLine(long id, LineDTO lineDTO) throws DAOException {
		
		Line found = lineRepository.findById(id).orElseThrow(() -> 
					new DAOException("Stations cannot be added because line [id=" + id + "] cannot be found.", HttpStatus.CONFLICT));
		
		//order   stationID
		//================
		// 1   :  4
		// 2   :  10
		ArrayList<Long> stationIDs = new ArrayList<Long>();
		for (Long sid : lineDTO.getStations().values()) {
			stationIDs.add(sid);
		}
		
		// remove duplicates bcs of DB findAll()...
		HashSet<Long> temp = new HashSet<Long>();
		temp.addAll(stationIDs);
		stationIDs.clear();
		stationIDs.addAll(temp);

		
		ArrayList<Station> foundStations = stationRepository.findByIdIn(stationIDs);
		
		if (foundStations.size() != stationIDs.size()) {
			throw new DAOException("Some stations not found in the DB!", HttpStatus.CONFLICT);
		}
		
		for (Integer key : lineDTO.getStations().keySet()) {
			Long stID = lineDTO.getStations().get(key);
			for (Station fs : foundStations) {
				if (fs.getId().equals(stID)) {
					found.addStation(fs, key);
				}
			}
		}	
		
		return lineRepository.save(found);	
	}
	
	@Override
	public Line updateLineStations(long id, LineDTO lineDTO) {
		Line l = findById(id);
		l.setStationSet(new HashSet<LineAndStation>());
		lineRepository.save(l);
		return addStationsToLine(id, lineDTO);
		
	}

	
	@Override
	public RouteSchedule findScheduleById(Long id) {
		RouteSchedule schedule = scheduleRepository.findById(id).orElseThrow(() -> 
								new DAOException("Schedule [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));
		return schedule;
	}
	

	@Override
	public List<RouteSchedule> getScheduleByLine(long id) {
		Line l = lineRepository.findById(id).orElseThrow(() -> 
								new DAOException("Line [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -32);
		Date earlierDate = c.getTime();
		
		List<RouteSchedule> availableSchedules = scheduleRepository.findByActiveFromGreaterThanAndLine(earlierDate, l);
		return availableSchedules;
	}



	@Override
	public RouteSchedule addSchedule(RouteSchedule schedule, long lineId) {
		Line l = findById(lineId);
		schedule.setLine(l);
		return scheduleRepository.save(schedule);
	}

	@Override
	public RouteSchedule updateSchedule(RouteSchedule updatedSchedule, long lineId, long scheduleId) {
		RouteSchedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> 
								new DAOException("Schedule [id=" + scheduleId + "] for line [id=" + lineId + 
												 "] cannot be found!", HttpStatus.CONFLICT));
		//Line l = findById(lineId);
		//schedule.setLine(l);
		schedule.setactiveFrom(updatedSchedule.getactiveFrom());
		schedule.setWeekday(updatedSchedule.getWeekday());
		schedule.setSaturday(updatedSchedule.getSaturday());
		schedule.setSunday(updatedSchedule.getSunday());
		return scheduleRepository.save(schedule);
	}

	@Override
	public boolean deleteSchedule(long id) {
		RouteSchedule schedule = findScheduleById(id);
		
		schedule.setActive(false);
		scheduleRepository.save(schedule);
		return true;
	}

	@Override
	public Zone getZoneForLine(Line line) {
		Collection<Station> stations = new HashSet<Station>();
		for (LineAndStation ls : line.getStationSet()) {
			stations.add(ls.getStation());
		}
		
		// zone kojima pripadaju stanice
		Set<Zone> zones = zoneService.getZonesByStations(stations);
		Zone parent = null;
		boolean flag;
		for (Zone potentialParent : zones) {
			flag = false;
			for (Zone zone : zones) {
				if (zone.getSubZone().equals(potentialParent)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				continue;
			}
			parent = potentialParent;
		}
		
		return parent;
	}
	


	
}
