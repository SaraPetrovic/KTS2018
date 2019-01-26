package ftn.kts.transport.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

	Set<Route> findByLine(Line l);
}
