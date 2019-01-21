package ftn.kts.transport.repositories;

import ftn.kts.transport.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

}
