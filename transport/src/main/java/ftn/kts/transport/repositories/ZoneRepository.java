package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Zone;

@Component
public interface ZoneRepository extends JpaRepository<Zone, Long>{

}
