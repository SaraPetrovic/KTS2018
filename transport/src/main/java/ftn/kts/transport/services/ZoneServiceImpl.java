package ftn.kts.transport.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.ZoneNotFoundException;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.repositories.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService{

	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public Zone addZone(Zone zone) {
		List<Zone> zones = findAll();

		Zone rez = zoneRepository.save(zone);
		
		for(Zone z : zones) {
			if(z.getSubZone() != null && zone.getSubZone() != null) {
				if(z.getSubZone().getId() == zone.getSubZone().getId()) {
					z.setSubZone(zone);
					zoneRepository.save(z);
				}
			}
		}
		return rez;
	}

	@Override
	public Zone save(Zone zone) {
		return zoneRepository.save(zone);
	}

	@Override
	public boolean deleteZone(Long id) {
		Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));
		
		if(zone.getStations().size() != 0) {
			throw new DAOException("Zone[id=" + id + "] can not be deleted, because it contains stations!",
					HttpStatus.BAD_REQUEST);
		}
		
		Zone subzone = zone.getSubZone();
		
		//podzona zone = zona koja se brise -> podzona zone = podzona zone koja se brise
		for(Zone z : findAll()) {
			if(z.getSubZone() != null && z.getSubZone().getId() == id) {
				z.setSubZone(subzone);
				zoneRepository.save(z);
			}
		}
		zone.setActive(false);
		zoneRepository.save(zone);
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

	@Override
	public Set<Zone> getZonesByStations(Collection<Station> stations) {
		Set<Zone> found = zoneRepository.findByStationsIn(stations);
		return found;
	}

}