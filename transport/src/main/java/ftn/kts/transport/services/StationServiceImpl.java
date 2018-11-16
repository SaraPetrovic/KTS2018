package ftn.kts.transport.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.StationDTO;
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
		Station s = stationRepository.findById(id).get();
		if(s.isActive()) {
			return s;
		}
		return null;
	}

	@Override
	public Station findByName(String name) {
		if(stationRepository.findByName(name).isActive()) {
			return stationRepository.findByName(name);
		}
		return null;
	}

	@Override
	public List<Station> findAll() {
		List<Station> stations = stationRepository.findAll().stream().filter(s -> s.isActive()).collect(Collectors.toList());
		return stations;
	}

	@Override
	public void delete(Long id) {
		Station s = stationRepository.findById(id).get();
		s.setActive(false);
		stationRepository.save(s);
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
