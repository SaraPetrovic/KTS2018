package ftn.kts.transport.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.model.Station;

@Service
public interface StationService {
	Station fromDtoToStation(StationDTO dtoStation);
	Station findById(Long id);
	Station findByName(String name);
	List<Station> findAll();
	boolean delete(Long id);
	Station save(Station station);
}
