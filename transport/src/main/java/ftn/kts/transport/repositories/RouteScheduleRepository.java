package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.transport.model.RouteSchedule;

public interface RouteScheduleRepository extends JpaRepository<RouteSchedule, Long>{

}
