package ftn.kts.transport.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;
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
	public Line addLine(LineDTO line) throws DAOException {
		
		// dodaj ogranicenje za active
		// npr ako se dodaje ponovo neka a ona je inactive,
		// samo je postavis na active i tjt
		
		
		Line newLine = new Line();
		newLine.setName(line.getName());
		newLine.setTransportType(VehicleType.values()[line.getVehicleType()]);
		Line l = null;
		
		try {
			l = lineRepository.save(newLine);
			return l;
		} catch(DataIntegrityViolationException e) {
			throw new DAOException("Duplicate entry for line [" + newLine.getName() + "]");
		} 
	}
	
	
	
	@Override
	public Line updateLine(LineDTO line, long id) throws DAOException {
		Line found = lineRepository.findById(id).orElseThrow(() -> 
						new DAOException("Line [id=" + id + "] cannot be updated because it cannot be found.", HttpStatus.CONFLICT));
		
		found.setName(line.getName());
		// dodaj jos za stanice kad se izmene
		lineRepository.save(found);
		return found;
		
	}
	
	@Override
	public Line deleteLine(LineDTO line) throws DAOException {
		// proveri da li sme da se brise (zbog ruta itd..)
		Line found = lineRepository.findByName(line.getName());
		if (found == null) {
			throw new DAOException("Line cannot be deleted because it cannot be found.", HttpStatus.CONFLICT);
		}
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

	
	
}
