package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.transport.model.LineAndStation;

public interface LineAndStationRepository extends JpaRepository<LineAndStation, LineAndStation.LineAndStationPK> {

}
