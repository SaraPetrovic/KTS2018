package ftn.kts.transport.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.VehicleDTO;
import ftn.kts.transport.model.Vehicle;
import ftn.kts.transport.repositories.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService{

	@Autowired
	private VehicleRepository vehicleRepository;
	
	
	@Override
	public List<Vehicle> findAll() {
		List<Vehicle> vehicles = vehicleRepository.findAll().stream().filter(v -> v.isActive()).collect(Collectors.toList());
		return vehicles;
	}


	@Override
	public Vehicle addVehicle(VehicleDTO vehicle) {
		if (vehicleRepository.findByVehicleName(vehicle.getName()) != null) {
			return null;
		}
		Vehicle newVehicle = new Vehicle();
		newVehicle.setVehicleName(vehicle.getName());
		return vehicleRepository.save(newVehicle);	
	}


	@Override
	public Vehicle updateVehicle(VehicleDTO vehicle, long id) {
		Vehicle found = vehicleRepository.getOne(id);
		if (found == null) {
			return null;
		}
		found.setVehicleName(vehicle.getName());
		vehicleRepository.save(found);
		return found;
	}


	@Override
	public Vehicle deleteVehicle(VehicleDTO vehicle) {
		Vehicle found = vehicleRepository.findByVehicleName(vehicle.getName());
		if (found == null) {
			return null;
		}
		found.setActive(false);
		vehicleRepository.save(found);
		return found;
	}
}
