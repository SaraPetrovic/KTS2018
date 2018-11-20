package ftn.kts.transport.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.RouteDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.repositories.LineRepository;

@Service
public class TransportNetworkServiceImpl implements TransportNetworkService {

	@Autowired
	private LineRepository lineRepository;
//	@Autowired
//	private RouteRepository routeRepository;
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	
	@Override
	public Line addLine(LineDTO line) {
		
		// dodaj ogranicenje za active
		// npr ako se dodaje ponovo neka a ona je inactive,
		// samo je postavis na active i tjt
		
		if (lineRepository.findByName(line.getName()) != null) {
			return null;
		}
		Line newLine = new Line();
		newLine.setName(line.getName());
		return lineRepository.save(newLine);	
	}
	
	@Override
	public Line updateLine(LineDTO line, long id) {
		//Long id = Long.parseLong(idStr);
		Line found = lineRepository.getOne(id);
		if (found == null) {
			return null;
		}
		found.setName(line.getName());
		// dodaj jos za stanice kad se izmene
		lineRepository.save(found);
		return found;
	}
	
	@Override
	public Line deleteLine(LineDTO line) {
		// proveri da li sme da se brise (zbog ruta itd..)
		Line found = lineRepository.findByName(line.getName());
		if (found == null) {
			return null;
		}
		found.setActive(false);
		lineRepository.save(found);
		return found;
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
