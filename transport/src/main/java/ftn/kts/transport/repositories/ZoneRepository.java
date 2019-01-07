package ftn.kts.transport.repositories;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

@Component
public interface ZoneRepository extends JpaRepository<Zone, Long>{

	Set<Zone> findByStationsIn(Collection<Station> stations);
}
