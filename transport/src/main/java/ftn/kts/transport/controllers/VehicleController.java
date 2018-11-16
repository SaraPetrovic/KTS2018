package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.dtos.VehicleDTO;
import ftn.kts.transport.model.Vehicle;
import ftn.kts.transport.services.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping(path="/all")
	@Produces("application/json")
	public ResponseEntity<List<VehicleDTO>> getAll(){
		List<VehicleDTO> dtoVehicles = new ArrayList<>();
		for(Vehicle v : vehicleService.findAll()) {
			dtoVehicles.add(new VehicleDTO(v));
		}
		return new ResponseEntity<>(dtoVehicles, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/add",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Vehicle> addVehicle(@RequestBody VehicleDTO newVehicle) {
		Vehicle ret = vehicleService.addVehicle(newVehicle);
		if (ret == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<Vehicle>(ret, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/{id}/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleDTO updatedVehicle, @PathVariable("id") long id) {
		Vehicle ret = vehicleService.updateVehicle(updatedVehicle, id);
		if (ret == null) {
			return new ResponseEntity<Vehicle>(ret, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Vehicle>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/delete",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Vehicle> deleteLine(@RequestBody VehicleDTO toDeleteVehicle) {
		Vehicle ret = vehicleService.deleteVehicle(toDeleteVehicle);
		if (ret == null) {
			return new ResponseEntity<Vehicle>(ret, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Vehicle>(ret, HttpStatus.OK);
	}
}
