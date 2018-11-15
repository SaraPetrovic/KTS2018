package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Route;

@Component
public interface RouteRepository extends JpaRepository<Route, Long>{
	
	Route findByName(String name);
	
}
