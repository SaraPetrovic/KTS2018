package ftn.kts.transport.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Line;

@Component
public interface LineRepository extends JpaRepository<Line, Long>{
	
	Line save(Line line);
	Line findByName(String name);	
	Optional<Line> findById(Long id);
}
