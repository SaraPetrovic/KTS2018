package ftn.kts.transport.services;

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
	
	
	public Station fromDtoToStation(StationDTO dtoStation) {
		
		if(dtoStation.getName() != null) {
			return stationRepository.findByName(dtoStation.getName());
		}else if(dtoStation.getId() != null) {
			return stationRepository.findById(dtoStation.getId()).get();
		}
		
		return null;
	}

	@Override
	public Station findById(Long id) {
		Station station = stationRepository.findById(id).get();
		if(station != null && station.isActive()) {
			return station;
		}
		return null;		
	}

	@Override
	public Station findByName(String name) {
		Station station = stationRepository.findByName(name);
		if(station != null && station.isActive()) {
			return station;
		}
		return null;
	}

	@Override
	public List<Station> findAll() {
		List<Station> stations = stationRepository.findAll().stream().filter(s -> s.isActive()).collect(Collectors.toList());
		return stations;
	}

	@Override
	public boolean delete(Long id) {
		Station station = stationRepository.findById(id).get();
		if(station == null) {
			return false;
		}
		station.setActive(false);
		stationRepository.save(station);
		return true;
	}

	@Override
	public void save(Station station) {
		stationRepository.save(station);
	}

	@Override
	public Station update(StationDTO dtoStation, Long id) {
		Station s = stationRepository.findById(id).get();
		s.setAddress(dtoStation.getAddress());
		s.setName(dtoStation.getName());
		
		//LINIJE
		stationRepository.save(s);
		return s;
	}
	
}
