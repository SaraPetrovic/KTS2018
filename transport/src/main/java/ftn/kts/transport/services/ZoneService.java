package ftn.kts.transport.services;

import java.util.List;

import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public interface ZoneService {
	void save(Zone zone);
	void deleteZone(Long id);
	Zone findById(Long id);
	List<Zone> findAll();
	void addStations(Zone zone, List<Station> stations);
	Zone update(ZoneDTO z, Long id);
}
