package ftn.kts.transport.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Station;

@Component
public interface StationRepository extends JpaRepository<Station, Long>{
	Station findByName(String name);
	ArrayList<Station> findByIdIn(ArrayList<Long> stationIdList);
	Station findById(long id);
}
