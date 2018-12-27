package ftn.kts.transport.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.repositories.StationRepository;

@Service
public class StationServiceImpl implements StationService{

	@Autowired
	private StationRepository stationRepository;
	
	@Override
	public Station fromDtoToStation(StationDTO dtoStation) {
		
		if(dtoStation.getId() != null) {
			return findById(dtoStation.getId());
		}else if(dtoStation.getName() != null) {
			return findByName(dtoStation.getName());
		}
		
		return null;
	}
	
	@Override
	public Station save(Station station) {
		return stationRepository.save(station);
	}

	@Override
	public Station findById(Long id) {
		Station station = stationRepository.findById(id).orElseThrow(() -> new StationNotFoundException(id));
		if(!station.isActive()) {
			throw new StationNotFoundException(id);
		}
		return station;		
	}

	@Override
	public Station findByName(String name) {
		Station station = stationRepository.findByName(name);
		if(station == null || !station.isActive()) {
			throw new StationNotFoundException(null);
		}
		return station;
	}

	@Override
	public List<Station> findAll() {
		//List<Station> stations = stationRepository.findAll().stream().filter(s -> s.isActive()).collect(Collectors.toList());
		List<Station> stations = new ArrayList<Station>();
		for(Station s : stationRepository.findAll()) {
			if(s.isActive())
				stations.add(s);
		}
		return stations;
	}

	@Override
	public boolean delete(Long id) {
		Station station = stationRepository.findById(id).orElseThrow(() -> new StationNotFoundException(id));
		
		station.setActive(false);
		stationRepository.save(station);
		return true;
	}

}
