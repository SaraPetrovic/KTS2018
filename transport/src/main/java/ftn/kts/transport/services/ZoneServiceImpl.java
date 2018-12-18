package ftn.kts.transport.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.exception.ZoneNotFoundException;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService{
	
	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public Zone save(Zone zone) {
		return zoneRepository.save(zone);
	}

	@Override
	public boolean deleteZone(Long id) {
		Zone z = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));
		
		z.setActive(false);
		zoneRepository.save(z);
		return true;
	}

	@Override
	public Zone findById(Long id) throws ZoneNotFoundException {
		Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));
		if(!zone.isActive()) {
			 throw new ZoneNotFoundException(id);
		}
		return zone;
		
	}

	@Override
	public List<Zone> findAll() {
		//List<Zone> zones = zoneRepository.findAll().stream().filter(s -> s.isActive()).collect(Collectors.toList());
		List<Zone> zones = new ArrayList<Zone>();
		for(Zone z : zoneRepository.findAll()) {
			if(z.isActive())
				zones.add(z);
		}
		return zones;
	}
	
}
