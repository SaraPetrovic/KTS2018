package ftn.kts.transport.repositories;


import java.util.List;
import java.util.Optional;

import ftn.kts.transport.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Line;

@Component
public interface LineRepository extends JpaRepository<Line, Long>{
	
	Optional<Line> findByName(String name);	
	Optional<Line> findById(Long id);
	List<Line> findByTransportType(VehicleType type);
}
