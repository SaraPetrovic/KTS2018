package ftn.kts.transport.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.exception.DAOException;
import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.StationRepository;

@Service
public class TransportNetworkServiceImpl implements TransportNetworkService {

	@Autowired
	private LineRepository lineRepository;
//	@Autowired
//	private RouteRepository routeRepository;
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
		Line l = null;
		
		try {
			l = lineRepository.save(newLine);
			return l;
		} catch(DataIntegrityViolationException e) {
			throw new DAOException("Duplicate entry '" + newLine.getName() + "'");
		} catch(DAOException e) {
			throw new DAOException("Stations error....");
		}
	}
	
	
	
	@Override
	public Line updateLine(LineDTO line, long id) throws DAOException {
			
		Line found = lineRepository.findById(id);
		if (found == null) {
			throw new DAOException("Entity cannot be updated because it cannot be found.", HttpStatus.CONFLICT);
		}
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



	@Override
	public Line addStationsToLine(long id, LineDTO lineDTO) throws DAOException {
		Line found = lineRepository.findById(id);
		if (found == null) {
			throw new DAOException("Stations cannot be added because line cannot be found.", HttpStatus.CONFLICT);
		}
		// ovde dobijem listu iz lineDTO.getStations():
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

		//HashMap<Integer, Station> map = new HashMap<Integer, Station>();
		
		ArrayList<Station> foundStations = stationRepository.findByIdIn(stationIDs);
		
		if (foundStations.size() != stationIDs.size()) {
			throw new DAOException("Some stations not found in the DB!", HttpStatus.CONFLICT);
		}
		
		for (Integer key : lineDTO.getStations().keySet()) {
			Long stID = lineDTO.getStations().get(key);
			for (Station fs : foundStations) {
				if (fs.getId().equals(stID)) {
			//		map.put(key, fs);
					found.addStation(fs, key);
				}
			}
		}	
		
		return lineRepository.save(found);
	//	return null;
		
	}
	
//	@Override
//	public Route addRoute(RouteDTO route) {
//		
//		//  dodas sva ogranicenja i sve atribute koji fale
//		//  za datume obavezno (start < end itd)
//		
//		
//		if (routeRepository.findByName(route.getName()) != null) {
//			return null;
//		}
//
//		Route newRoute = new Route();
//		newRoute.setName(route.getName());
//		try {
//			newRoute.setStartTime(formatter.parse(route.getStartTime()));
//			newRoute.setEndTime(formatter.parse(route.getEndTime()));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		newRoute.setLine(lineRepository.findByName(route.getLine()));
//		//newRoute.setVehicle(lineRepository.findByName(route.getVehicle()));
//		routeRepository.save(newRoute);
//		return newRoute;
//	}
//	
//	@Override
//	public Route updateRoute(RouteDTO route, long id) {
//		
//			//  dodas sva ogranicenja i sve atribute koji fale
//			//  za datume obavezno (start < end itd)
//		
//		Route found = routeRepository.getOne(id);
//		if (found == null) {
//			return null;
//		}
//		found.setName(route.getName());
//		found.setLine(lineRepository.findByName(route.getLine()));
//		try {
//			found.setStartTime(formatter.parse(route.getStartTime()));
//			found.setEndTime(formatter.parse(route.getEndTime()));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		// found.setVehicle()......................
//		
//		routeRepository.save(found);
//		return found;
//	}
//	
//	@Override
//	public Route deleteRoute(RouteDTO route) {
//		// proveri da li sme da se brise 
//		Route found = routeRepository.findByName(route.getName());
//		if (found == null) {
//			return null;
//		}
//		found.setActive(false);
//		routeRepository.save(found);
//		return found;
//	}
	
	
	
}
