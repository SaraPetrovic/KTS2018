package ftn.kts.transport.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public interface ZoneService {
	Zone save(Zone zone);
	boolean deleteZone(Long id);
	Zone findById(Long id);
	List<Zone> findAll();
	Set<Zone> getZonesByStations(Collection<Station> stations);
	Zone addZone(Zone zone);
	Zone getZoneForLine(Line line);
}
