package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Line;

@Component
public interface LineRepository extends JpaRepository<Line, Long>{
	
	Line findByName(String name);
	

}
