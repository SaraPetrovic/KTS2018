package ftn.kts.transport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity checkIn(@PathVariable("vehicleNumber") String vehicleNumber) {
		// Vehicle vehicle = this.vehicleService.findById(Long.valueOf(vehicleNumber));
		// Route route = routeService..getRouteByVehicle(vehicle);
		//return ResponseEntity.ok(route);
		return null;
	}
}
