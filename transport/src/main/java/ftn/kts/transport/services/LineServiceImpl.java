package ftn.kts.transport.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.StationRepository;

@Service
public class LineServiceImpl implements LineService {

	@Autowired
	private LineRepository lineRepository;
	@Autowired
	private StationRepository stationRepository;
	
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
	public Line findByName(String name) {
		return lineRepository.findByName(name).orElseThrow(() -> 
									new DAOException("Line [name= " + name + "] already exists!", HttpStatus.CONFLICT));
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


	@Transactional
	@Override
	public Line addLineMethod(Line line, LineDTO lineDTO) {
		Line ret = addLine(line);
		ret = addStationsToLine(ret.getId(), lineDTO);
		return ret;
	}


	@Override
	public Set<Line> getAllLinesByZoneAndTransportType(Zone zone, VehicleType type) {
		Set<Line> ret = new HashSet<Line>();
		Collection<Line> lines = new ArrayList<Line>();
		for (Station s : zone.getStations()) {
			for (LineAndStation ls : s.getLineSet()) {
				if (ls.getLine().getTransportType().ordinal() == type.ordinal()) {
					lines.add(ls.getLine());
				}	
			}
		}
		
		ret.addAll(lines);
		return ret;
	}
	


	
}
