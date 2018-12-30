package ftn.kts.transport.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.ZoneDTO;
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
	public Zone save(Zone zone) {
		return zoneRepository.save(zone);
	}

	@Override
	public boolean deleteZone(Long id) {
		Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));
		
		if(zone.getSubZone() == null) {
			throw new DAOException("Zone[id=" + id + "] contains stations and does not contain a subzone. Can not be deleted!",
					HttpStatus.BAD_REQUEST);
		}
		Zone subzone = zone.getSubZone();
		if(zone.getStations() != null) {
			if(subzone.getStations() == null) {
				subzone.setStations(new HashSet<Station>());
			}
			if(zone.getStations().size() != 0) {
				//stanice se dodaju u stanice podzone
				for(Station s : zone.getStations()) {
					subzone.getStations().add(s);
				}
				zoneRepository.save(subzone);
			}
		}
		//promeni se podzona zone, kojoj je zona koja se brise bila podzona
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

}