package ftn.kts.transport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;

public interface RouteScheduleRepository extends JpaRepository<RouteSchedule, Long>{

	List<RouteSchedule> findByActiveAndLine(boolean active, Line line);
}
