package ftn.kts.transport.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService{
	
	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public void save(Zone zone) {
		zoneRepository.save(zone);
	}

	@Override
	public void deleteZone(Long id) {
		Zone z = zoneRepository.findById(id).get();
		z.setActive(false);
		zoneRepository.save(z);
	}

	@Override
	public Zone findById(Long id) {
		if(zoneRepository.findById(id).get().isActive()) {
			return zoneRepository.findById(id).get();
		}
		return null;
	}

	@Override
	public List<Zone> findAll() {
		List<Zone> zones = zoneRepository.findAll().stream().filter(s -> s.isActive()).collect(Collectors.toList());
		return zones;
	}

	@Override
	public void addStations(Zone zone, List<Station> stations) {
		zone.setStations(new HashSet(stations));
		zoneRepository.save(zone);

	}
	
}
