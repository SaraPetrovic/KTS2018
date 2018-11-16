package ftn.kts.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.Vehicle;

@Component
public interface VehicleRepository extends  JpaRepository<Vehicle, Long> {
	Vehicle findByVehicleName(String name);

}
