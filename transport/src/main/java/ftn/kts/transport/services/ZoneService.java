package ftn.kts.transport.services;

import java.util.List;

import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public interface ZoneService {
	Zone save(Zone zone);
	boolean deleteZone(Long id);
	Zone findById(Long id);
	List<Zone> findAll();
}
