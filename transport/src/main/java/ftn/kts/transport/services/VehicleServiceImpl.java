package ftn.kts.transport.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.VehicleDTO;
import ftn.kts.transport.exception.DAOException;
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
	public boolean deleteVehicle(long id) {
		Vehicle found = vehicleRepository.findById(id).orElseThrow(() -> 
								new DAOException("Vehicle [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));

		found.setActive(false);
		vehicleRepository.save(found);
		return true;
	}

	@Override
	public Vehicle getFreeVehicle(Date date, int duration){

		List<Vehicle> vehicles = this.vehicleRepository.findAll();
		for(Vehicle vehicle : vehicles){
			if(vehicle.isFree() || vehicle.getFreeFrom().before(date)){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.SECOND, duration);

				vehicle.setFree(false);
				vehicle.setFreeFrom(c.getTime());

				this.vehicleRepository.save(vehicle);

				return  vehicle;
			}
		}
		return null;
	}
}
