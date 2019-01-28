package ftn.kts.transport.controllers;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.services.RouteService;
import ftn.kts.transport.services.VehicleService;

@RestController
public class ConductorController {
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping(value = "rest/conductor/checkin/{vehicleNumber}",
			produces = "application/json")
	@PreAuthorize("hasRole('CONDUCTER')")
	public ResponseEntity checkIn(@PathVariable("vehicleNumber") String vehicleNumber) {
		Vehicle vehicle = this.vehicleService.findById(Long.valueOf(vehicleNumber));
		if(vehicle == null){
			throw new DAOException("Vehicile for that id not found", HttpStatus.NOT_FOUND);
		}
		Route route = routeService.getRouteByVehicle(vehicle);
		if(route != null) {
			return ResponseEntity.ok(route);
		}else{
			throw new DAOException("Route for that vehicle not found", HttpStatus.NOT_FOUND);
		}
	}
}
